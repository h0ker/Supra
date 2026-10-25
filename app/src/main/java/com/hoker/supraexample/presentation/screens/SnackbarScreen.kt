package com.hoker.supraexample.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.hoker.supra.presentation.buttons.SupraButtonTone
import com.hoker.supra.presentation.buttons.SupraHardwareButton
import com.hoker.supra.presentation.snackbars.SupraSnackbar
import com.hoker.supra.presentation.theme.success
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel
import com.hoker.supraexample.presentation.viewmodels.SupraFXViewModel

private data class SnackbarRequest(val message: String, val borderColor: Color)

@Composable
fun SnackbarScreen(
    viewModel: SupraFXViewModel = hiltViewModel()
) {
    val successColor = MaterialTheme.colorScheme.success
    val errorColor = MaterialTheme.colorScheme.error

    var request by remember { mutableStateOf<SnackbarRequest?>(null) }
    // Bumped on every fire so the countdown restarts even when a snackbar is already showing
    var fireCount by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ScreenColumn {
            SpecimenLabel(text = "Border drains as the timeout runs out")
            SupraHardwareButton(
                text = "Success",
                tone = SupraButtonTone.NEUTRAL,
                fullWidth = true,
                onClick = {
                    request = SnackbarRequest("Key written to slot 1", successColor)
                    fireCount++
                }
            )
            SupraHardwareButton(
                text = "Error",
                tone = SupraButtonTone.NEUTRAL,
                fullWidth = true,
                onClick = {
                    request = SnackbarRequest("Write failed · tag moved", errorColor)
                    fireCount++
                }
            )
            SpecimenLabel(text = "Scaffold snackbar via SupraFX · accent border")
            SupraHardwareButton(
                text = "Via SupraFX",
                tone = SupraButtonTone.QUIET,
                fullWidth = true,
                onClick = {
                    viewModel.supraFX.showSnackbar("Slot 2 erased")
                }
            )
        }

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            visible = request != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            val current = request
            if (current != null) {
                key(fireCount) {
                    SupraSnackbar(
                        message = current.message,
                        borderColor = current.borderColor,
                        onDismiss = { request = null }
                    )
                }
            }
        }
    }
}
