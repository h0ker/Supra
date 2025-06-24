package com.hoker.supraexample.presentation.viewmodels

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoker.supra.domain.OverlayState
import com.hoker.supra.presentation.fx.SupraFX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupraFXViewModel @Inject constructor(
    val supraFX: SupraFX
) : ViewModel() {

    fun startIndeterminateLoading5Sec() {
        viewModelScope.launch {
            supraFX.setLoadingState(OverlayState.LOADING_INDETERMINATE)
            delay(5000)
            supraFX.setLoadingState(OverlayState.INACTIVE)
        }
    }

    fun showScanPrompt() {
        viewModelScope.launch {
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
        supraFX.showScanPromptWithCustomContent(
            content = content
        )
    }
}