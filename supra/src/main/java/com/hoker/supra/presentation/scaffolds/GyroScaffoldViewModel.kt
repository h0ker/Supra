package com.hoker.supra.presentation.scaffolds

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class GyroScaffoldViewModel @Inject constructor(
    private val sensorManager: SensorManager,
    accelerometer: Sensor?
) : ViewModel(), SensorEventListener {

    companion object {
        const val MAX_TRANSLATION = 6f
        const val GYRO_THRESHOLD = 0.5f
    }

    private val _translationData = MutableStateFlow(0f to 0f)
    val translationData = _translationData.asStateFlow()

    init {
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val rotationRateX = it.values[0]
            val rotationRateY = it.values[1]

            if (abs(rotationRateX) > GYRO_THRESHOLD || abs(rotationRateY) > GYRO_THRESHOLD) {

                val translationX = rotationRateX.coerceIn(-MAX_TRANSLATION, MAX_TRANSLATION)
                val translationY = rotationRateY.coerceIn(-MAX_TRANSLATION, MAX_TRANSLATION)

                _translationData.value = Pair(translationX, translationY)
            } else if (_translationData.value.first != 0f || _translationData.value.second != 0f) {
                _translationData.value = Pair(0f, 0f)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }
}