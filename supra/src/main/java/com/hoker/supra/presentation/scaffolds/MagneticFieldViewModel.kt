package com.hoker.supra.presentation.scaffolds

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import com.hoker.supra.presentation.fx.SupraFX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.math.sqrt

@HiltViewModel
class MagneticFieldViewModel @Inject constructor(
    private val sensorManager: SensorManager,
    val supraFX: SupraFX
) : ViewModel(), SensorEventListener {

    companion object {
        /**
         * Narrowest window the rail will range over. Earth's field is ~25-65 µT, but what reaches the
         * sensor depends on the case, the speaker magnets and the device's own calibration, so the
         * absolute number says more about the hardware than about the moment. Below this span the
         * reading is sensor noise, and the rail should sit still rather than amplify it.
         */
        const val MIN_SPAN_MICROTESLA = 25f

        /** Low-pass on the raw magnitude, so the head tick doesn't jitter. */
        private const val SMOOTHING = 0.15f

        /**
         * How fast the window closes back in on the current reading, per sample. Slow on purpose: a
         * magnet waved past the phone widens the range instantly, and it takes ~10s to forget it again.
         * Faster than this and the window starts chasing slow movement, flattening the read.
         */
        private const val RELAX = 0.02f
    }

    private val magneticSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    val hasSensor: Boolean = magneticSensor != null

    private var listening = false
    private var smoothedMagnitude = 0f

    // The range this device has actually shown. Seeded from the first sample, never from a constant:
    // a fixed full scale pins the rail at either end on any device whose baseline sits outside it.
    private var ranged = false
    private var floor = 0f
    private var ceiling = 0f

    private val _normalisedField = MutableStateFlow(0f)

    /**
     * Where the field sits inside the range this device has shown, 0f..1f. A resting device reads
     * about 0.5 and moves either way, rather than parking at one end of the rail.
     */
    val normalisedField: StateFlow<Float> = _normalisedField.asStateFlow()

    fun start() {
        if (listening) return
        magneticSensor?.let {
            listening = sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stop() {
        if (!listening) return
        sensorManager.unregisterListener(this)
        listening = false
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        sensorEvent?.let { event ->
            if (event.sensor.type != Sensor.TYPE_MAGNETIC_FIELD) return

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val magnitude = sqrt(x * x + y * y + z * z)

            if (!ranged) {
                // Start from where this device actually is, so the rail opens mid-scale
                smoothedMagnitude = magnitude
                floor = magnitude
                ceiling = magnitude
                ranged = true
            } else {
                smoothedMagnitude += (magnitude - smoothedMagnitude) * SMOOTHING
            }

            _normalisedField.value = rangeOf(smoothedMagnitude)
            supraFX.updateMagneticInterference(smoothedMagnitude)
        }
    }

    /**
     * Auto-ranging: take in anything new immediately, then creep back in, so a one-off spike doesn't
     * flatten the rail for the rest of the session.
     */
    private fun rangeOf(value: Float): Float {
        if (value < floor) floor = value
        if (value > ceiling) ceiling = value

        // Both move toward the current value, so both move inward
        floor += (value - floor) * RELAX
        ceiling += (value - ceiling) * RELAX

        // Hold the window open around its middle. This is what keeps a still device reading 0.5
        // instead of swinging end to end on noise
        if (ceiling - floor < MIN_SPAN_MICROTESLA) {
            val middle = (floor + ceiling) / 2f
            floor = middle - MIN_SPAN_MICROTESLA / 2f
            ceiling = middle + MIN_SPAN_MICROTESLA / 2f
        }

        return ((value - floor) / (ceiling - floor)).coerceIn(0f, 1f)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // A recalibrating magnetometer can step to a completely different baseline. Drop the window
        // and rebuild it from the next sample rather than ranging over the discontinuity.
        if (accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            ranged = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        stop()
    }
}
