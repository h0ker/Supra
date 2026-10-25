package com.hoker.supraexample.presentation.viewmodels

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoker.supra.domain.OverlayState
import com.hoker.supra.presentation.fx.SupraFX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupraFXViewModel @Inject constructor(
    val supraFX: SupraFX
) : ViewModel() {

    private var overlayJob: Job? = null

    /**
     * Shows [state] and clears it after [durationMillis], unless it was dismissed or replaced first.
     */
    fun showOverlay(state: OverlayState, durationMillis: Long = 5000) {
        overlayJob?.cancel()
        overlayJob = viewModelScope.launch {
            supraFX.setLoadingState(state)
            delay(durationMillis)
            if (supraFX.overlayState.value == state) {
                supraFX.setLoadingState(OverlayState.INACTIVE)
            }
        }
    }

    fun showScanPrompt() {
        overlayJob?.cancel()
        overlayJob = viewModelScope.launch {
            supraFX.setLoadingState(OverlayState.SCAN_PROMPT)
            delay(5000)
            if (supraFX.overlayState.value != OverlayState.INACTIVE) {
                supraFX.setLoadingState(OverlayState.SCANNING)
                delay(5000)
                supraFX.setLoadingState(OverlayState.INACTIVE)
            }
        }
    }

    fun showScanPromptCustomContent(
        content: @Composable () -> Unit
    ) {
        overlayJob?.cancel()
        supraFX.showScanPromptWithCustomContent(
            content = content
        )
    }

    fun showCustomContent(
        content: @Composable () -> Unit
    ) {
        overlayJob?.cancel()
        supraFX.showOverlayWithCustomContent(content)
    }

    fun dismissOverlay() {
        overlayJob?.cancel()
        supraFX.setLoadingState(OverlayState.INACTIVE)
    }
}
