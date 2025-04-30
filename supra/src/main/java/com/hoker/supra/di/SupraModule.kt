package com.hoker.supra.di

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorManager
import com.hoker.supra.domain.Consts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupraModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SupraSharedPrefs

    @Provides
    fun provideSensorManager(@ApplicationContext context: Context): SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    @Provides
    fun provideGyroscopeSensor(sensorManager: SensorManager): Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    @Provides
    @Singleton
    @SupraSharedPrefs
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Consts.SUPRA_SHARED_PREF_KEY, Context.MODE_PRIVATE)
    }
}