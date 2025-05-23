package com.hoker.supra.presentation.indicators

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.animation.core.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color

@Composable
fun RandomLoadingGlyph(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    canvasSize: Dp = 32.dp,
    rotationStepDuration: Int = 200,
    pauseDuration: Long = 500L,
    activeColor: Color = MaterialTheme.colorScheme.tertiary
) {

    val color = if (isLoading) activeColor else MaterialTheme.colorScheme.onPrimary
    val glyph = LoadingGlyphs.glyphList.random()

    // animatable angle
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            while (true) {
                // rotate +90°
                rotation.animateTo(
                    targetValue = rotation.value + 90f,
                    animationSpec = tween(durationMillis = rotationStepDuration, easing = LinearEasing)
                )
                // pause
                delay(pauseDuration)
            }
        } else {
            // snap back to initial tilt when stopped
            rotation.snapTo(0f)
        }
    }

    Canvas(modifier = modifier.size(canvasSize)) {
        // center & rotate, then draw whatever the caller wants
        rotate(degrees = rotation.value, pivot = center) {
            glyph(this, color)
        }
    }
}