package com.hoker.supraexample.di

import android.content.Context
import android.content.SharedPreferences
import com.hoker.supraexample.domain.models.Consts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SupraModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Consts.SHARED_PREFS_KEY, Context.MODE_PRIVATE)
    }
}