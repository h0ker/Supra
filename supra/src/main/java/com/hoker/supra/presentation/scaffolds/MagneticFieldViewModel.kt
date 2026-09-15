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
        /** Field strength that fills the rail. Earth's field sits around 25-65 µT. */
        const val FULL_SCALE_MICROTESLA = 200f
    }

    private val magneticSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    val hasSensor: Boolean = magneticSensor != null

    // Low-pass filter so the rail's head tick doesn't jitter on raw sensor noise
    private val smoothingFactor = 0.15f
    private var smoothedMagnitude = 0f
    private var listening = false

    private val _normalisedField = MutableStateFlow(0f)

    /** Smoothed field magnitude, 0f..1f of [FULL_SCALE_MICROTESLA]. */
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
            if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val currentMagnitude = sqrt(x * x + y * y + z * z)

                smoothedMagnitude += (currentMagnitude - smoothedMagnitude) * smoothingFactor

                _normalisedField.value = (smoothedMagnitude / FULL_SCALE_MICROTESLA).coerceIn(0f, 1f)
                supraFX.updateMagneticInterference(smoothedMagnitude)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //Calibration logic here if needed
    }

    override fun onCleared() {
        super.onCleared()
        stop()
    }
}
