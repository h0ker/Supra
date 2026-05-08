package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hoker.supra.presentation.controls.SettingsBooleanState
import com.hoker.supra.presentation.controls.SupraMultiSelector
import com.hoker.supra.presentation.fx.GlitchEffectImpl
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraBodyTextMedium
import com.hoker.supra.presentation.text.SupraTitleTextSmall
import com.hoker.supra.presentation.theme.success
import com.hoker.supraexample.presentation.viewmodels.SupraFXViewModel

@Composable
fun SupraFXScreen(
    viewModel: SupraFXViewModel = hiltViewModel()
) {

    val successColor = MaterialTheme.colorScheme.success
    val errorColor = MaterialTheme.colorScheme.error

    val isAudioEnabled = viewModel.supraFX.isAudioEnabled.collectAsState()
    val isVibrationEnabled = viewModel.supraFX.isVibrationEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Sizes.medium),
        verticalArrangement = Arrangement.spacedBy(Sizes.medium)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SupraTitleTextSmall(
                modifier = Modifier.padding(start = 16.dp),
                text = "Background Surface"
            )
            SupraMultiSelector(
                modifier = Modifier
                    .height(36.dp)
                    .width(150.dp),
                options = SettingsBooleanState.entries.map { it.displayName },
                selectedOption = SettingsBooleanState.fromBoolean(isAudioEnabled.value).displayName,
                onOptionSelect = { option ->
                    when (option) {
                        SettingsBooleanState.ENABLED.displayName -> viewModel.supraFX.setAudioSettingState(true)
                        SettingsBooleanState.DISABLED.displayName -> viewModel.supraFX.setAudioSettingState(false)
                    }
                }
            )
        }

        Button(
            onClick = {
                viewModel.startIndeterminateLoading5Sec()
            }
        ) {
            Text(
                text = "Show Indeterminate Loading Indicator (5 seconds)"
            )
        }

        Button(
            onClick = {
                viewModel.showScanPrompt()
            }
        ) {
            Text(
                text = "Show Scan Prompt Indicator (10 seconds)"
            )
        }

        Button(
            onClick = {
                viewModel.showScanPromptCustomContent {
                    Card(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            SupraBodyTextMedium(
                                text = "HMAC",
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            SupraBodyTextMedium(
                                text = "Writing key onto slot 1"
                            )
                        }
                    }
                }
            }
        ) {
            Text(
                text = "Show cancellable dialog with custom content"
            )
        }

        Button(
            onClick = {
                viewModel.supraFX.firePulse(successColor)
                viewModel.supraFX.playSuccessSound()
            }
        ) {
            Text(
                text = "Fire Outgoing Success Pulse"
            )
        }

        Button(
            onClick = {
                viewModel.supraFX.firePulse(errorColor)
                viewModel.supraFX.playErrorSound()
            }
        ) {
            Text(
                text = "Fire Outgoing Error Pulse"
            )
        }

        Button(
            onClick = {
                viewModel.supraFX.showSnackbar("This is a test snackbar")
            }
        ) {
            Text(
                text = "Show snackbar"
            )
        }

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SupraTitleTextSmall(
                modifier = Modifier.padding(start = 16.dp),
                text = "Sound Effects"
            )
            SupraMultiSelector(
                modifier = Modifier
                    .height(36.dp)
                    .width(150.dp),
                options = SettingsBooleanState.entries.map { it.displayName },
                selectedOption = SettingsBooleanState.fromBoolean(isAudioEnabled.value).displayName,
                onOptionSelect = { option ->
                    when (option) {
                        SettingsBooleanState.ENABLED.displayName -> viewModel.supraFX.setAudioSettingState(true)
                        SettingsBooleanState.DISABLED.displayName -> viewModel.supraFX.setAudioSettingState(false)
                    }
                }
            )
        }

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SupraTitleTextSmall(
                modifier = Modifier.padding(start = 16.dp),
                text = "Vibration Effects"
            )
            SupraMultiSelector(
                modifier = Modifier
                    .height(36.dp)
                    .width(150.dp),
                options = SettingsBooleanState.entries.map { it.displayName },
                selectedOption = SettingsBooleanState.fromBoolean(isVibrationEnabled.value).displayName,
                onOptionSelect = { option ->
                    when (option) {
                        SettingsBooleanState.ENABLED.displayName -> viewModel.supraFX.setVibrationSettingState(true)
                        SettingsBooleanState.DISABLED.displayName -> viewModel.supraFX.setVibrationSettingState(false)
                    }
                }
            )
        }

        GlitchEffectImpl()
    }
}