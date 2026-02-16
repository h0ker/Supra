package com.hoker.supra.presentation.scaffolds

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import com.hoker.supra.presentation.fx.SupraFX
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.sqrt

@HiltViewModel
class MagneticFieldViewModel @Inject constructor(
    private val sensorManager: SensorManager,
    val supraFX: SupraFX
) : ViewModel(), SensorEventListener {

    private var magneticSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private val smoothingFactor = 0.1f
    private var smoothedMagnitude = 0f

    init {
        magneticSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        sensorEvent?.let { event ->
            if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val currentMagnitude = sqrt(x * x + y * y + z * z)

                smoothedMagnitude += (currentMagnitude - smoothedMagnitude) * smoothingFactor

                supraFX.updateMagneticInterference(smoothedMagnitude)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //Calibration logic here if needed
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }
}