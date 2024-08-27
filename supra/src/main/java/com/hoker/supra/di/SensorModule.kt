package com.hoker.supra.di

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    @Provides
    fun provideSensorManager(@ApplicationContext context: Context): SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    @Provides
    fun provideAccelerometerSensor(sensorManager: SensorManager): Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
}