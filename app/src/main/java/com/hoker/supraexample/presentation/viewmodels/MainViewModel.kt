package com.hoker.supraexample.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.hoker.supraexample.domain.models.Consts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _isDarkModeEnabled: MutableStateFlow<Boolean> = MutableStateFlow(sharedPreferences.getBoolean(Consts.DARK_MODE_ENABLED, false))
    val isDarkModeEnabled = _isDarkModeEnabled.asStateFlow()

    fun toggleDarkMode() {
        with(sharedPreferences.edit()) {
            putBoolean(Consts.DARK_MODE_ENABLED, !_isDarkModeEnabled.value)
            apply()
        }
        _isDarkModeEnabled.value = !_isDarkModeEnabled.value
    }
}