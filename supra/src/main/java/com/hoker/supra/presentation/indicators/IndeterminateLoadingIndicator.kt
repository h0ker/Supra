package com.hoker.supra.presentation.indicators

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.hoker.supra.presentation.sizes.Sizes

@Composable
fun IndeterminateLoadingIndicator(
    show: Boolean,
    color: Color = MaterialTheme.colorScheme.onPrimary
) {
    AnimatedVisibility(
        visible = show,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RandomLoadingGlyph(
                modifier = Modifier.padding(bottom = Sizes.large),
                isLoading = true,
                activeColor = color
            )
        }
    }
}