package com.hoker.supra.presentation.fx

import android.content.Context
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupraFX @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _oneshotChannel = Channel<Color>(Channel.BUFFERED)
    val oneshotChannel: ReceiveChannel<Color> = _oneshotChannel

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setIsLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun firePulse(color: Color) {
        _oneshotChannel.trySend(color)
    }
}