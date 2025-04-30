package com.hoker.supraexample.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoker.supra.domain.LoadingState
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
            supraFX.setLoadingState(LoadingState.LOADING_INDETERMINATE)
            delay(5000)
            supraFX.setLoadingState(LoadingState.INACTIVE)
        }
    }

    fun showScanPrompt() {
        viewModelScope.launch {
            supraFX.setLoadingState(LoadingState.SCAN_PROMPT)
            delay(5000)
            if (supraFX.loadingState.value != LoadingState.INACTIVE) {
                supraFX.setLoadingState(LoadingState.SCANNING)
                delay(5000)
                supraFX.setLoadingState(LoadingState.INACTIVE)
            }
        }
    }
}