package com.hoker.supra.presentation.snackbars

import android.graphics.PathMeasure
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraBodyTextSmall
import kotlin.apply

@Composable
fun SupraSnackbar(
    modifier: Modifier = Modifier,
    message: String,
    durationMillis: Long = 5000L,
    onDismiss: () -> Unit,
    borderColor: Color,
    backgroundColor: Color,
    contentColor: Color,
    borderStrokeWidth: Dp = 4.dp,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    val progress = remember { Animatable(1f) }

    val layoutDirection = LocalLayoutDirection.current

    LaunchedEffect(durationMillis) {
        progress.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = durationMillis.toInt(),
                easing = LinearEasing
            )
        )
        onDismiss()
    }

    Box(
        modifier = modifier
            .padding(Sizes.medium)
            .drawBehind {
                // Get the layout direction (needed for creating the outline)
                // Create an outline based on our RoundedCornerShape
                val outline = shape.createOutline(size, layoutDirection, this)
                if (outline is Outline.Rounded) {
                    // Build a Compose Path following the rounded rectangle's outline.
                    val path = Path().apply {
                        addRoundRect(outline.roundRect)
                    }
                    // Convert the Compose Path to an Android Path so we can measure its length.
                    val androidPath = path.asAndroidPath()
                    val pathMeasure = PathMeasure(androidPath, false)
                    val totalLength = pathMeasure.length

                    // Create a dash effect that reveals only part of the border based on progress.
                    val dashEffect = PathEffect.dashPathEffect(
                        floatArrayOf(totalLength, totalLength),
                        totalLength * (1 - progress.value)
                    )

                    // Draw the animated border along the rounded outline.
                    drawPath(
                        path = path,
                        color = borderColor,
                        style = Stroke(
                            width = borderStrokeWidth.toPx(),
                            cap = StrokeCap.Round,
                            pathEffect = dashEffect
                        )
                    )
                }
            }
            .background(backgroundColor, shape = shape)
            .padding(Sizes.medium)
    ) {
        SupraBodyTextSmall(
            text = message,
            color = contentColor
        )
    }
}