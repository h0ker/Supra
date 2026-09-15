package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hoker.supra.presentation.buttons.SupraOutlinedButton
import com.hoker.supra.presentation.controls.SettingsBooleanState
import com.hoker.supra.presentation.fx.GlitchEffectImpl
import com.hoker.supra.presentation.theme.success
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SettingRow
import com.hoker.supraexample.presentation.components.SpecimenLabel
import com.hoker.supraexample.presentation.viewmodels.SupraFXViewModel

@Composable
fun SupraFXScreen(
    viewModel: SupraFXViewModel = hiltViewModel()
) {

    val successColor = MaterialTheme.colorScheme.success
    val errorColor = MaterialTheme.colorScheme.error

    val isAudioEnabled = viewModel.supraFX.isAudioEnabled.collectAsState()
    val isVibrationEnabled = viewModel.supraFX.isVibrationEnabled.collectAsState()

    ScreenColumn {
        SpecimenLabel(text = "Pulse · sound · haptics")
        SupraOutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Fire outgoing success pulse",
            onClick = {
                viewModel.supraFX.firePulse(successColor)
                viewModel.supraFX.playSuccessSound()
            }
        )
        SupraOutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Fire outgoing error pulse",
            onClick = {
                viewModel.supraFX.firePulse(errorColor)
                viewModel.supraFX.playErrorSound()
            }
        )
        SupraOutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Show snackbar",
            onClick = {
                viewModel.supraFX.showSnackbar("Key written to slot 1")
            }
        )

        SettingRow(
            label = "Sound effects",
            options = SettingsBooleanState.entries.map { it.displayName },
            selectedOption = SettingsBooleanState.fromBoolean(isAudioEnabled.value).displayName,
            onOptionSelect = { option ->
                viewModel.supraFX.setAudioSettingState(option == SettingsBooleanState.ENABLED.displayName)
            }
        )
        SettingRow(
            label = "Vibration effects",
            options = SettingsBooleanState.entries.map { it.displayName },
            selectedOption = SettingsBooleanState.fromBoolean(isVibrationEnabled.value).displayName,
            onOptionSelect = { option ->
                viewModel.supraFX.setVibrationSettingState(option == SettingsBooleanState.ENABLED.displayName)
            }
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            GlitchEffectImpl()
        }
    }
}
