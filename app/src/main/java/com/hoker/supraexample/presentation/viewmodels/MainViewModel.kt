package com.hoker.supraexample.presentation.viewmodels

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.hoker.supra.di.SupraModule
import com.hoker.supra.presentation.fx.SupraFX
import com.hoker.supraexample.domain.models.Consts
import com.hoker.supra.presentation.theme.UiTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @SupraModule.SupraSharedPrefs private val sharedPreferences: SharedPreferences,
    val supraFX: SupraFX
): ViewModel() {

    private val _isDarkModeEnabled: MutableStateFlow<Boolean> = MutableStateFlow(sharedPreferences.getBoolean(Consts.DARK_MODE_ENABLED, false))
    val isDarkModeEnabled = _isDarkModeEnabled.asStateFlow()

    val uiThemeSettingState = UiTheme.fromTitle(sharedPreferences.getString(Consts.SETTINGS_SELECTED_UI_THEME, UiTheme.VIVOKEY_BLUE.title))

    fun toggleDarkMode() {
        with(sharedPreferences.edit()) {
            putBoolean(Consts.DARK_MODE_ENABLED, !_isDarkModeEnabled.value)
            apply()
        }
        _isDarkModeEnabled.value = !_isDarkModeEnabled.value
    }

    fun updateSettingState(key: String, state: String) {
        sharedPreferences.edit {
            putString(key, state)
        }
    }
}