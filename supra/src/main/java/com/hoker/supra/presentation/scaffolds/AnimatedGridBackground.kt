package com.hoker.supra.presentation.scaffolds

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.tan

/**
 * Renders a full-size animated background of "+" symbols arranged in a grid
 * that continuously scrolls diagonally (up and to the left).
 *
 * The grid is drawn on a Canvas for performance — each "+" is drawn as two
 * small crossed lines rather than text, keeping the draw calls lightweight.
 */
@Composable
fun AnimatedGridBackground(
    config: SupraBackground.AnimatedGrid,
    fallbackColor: Color
) {
    val symbolColor = config.symbolColor ?: fallbackColor
    val density = LocalDensity.current

    val gridSpacingPx = with(density) { config.gridSpacing.dp.toPx() }
    val symbolSizePx = with(density) { config.symbolSize.sp.toPx() }
    val armLength = symbolSizePx / 2f
    val strokeWidth = symbolSizePx / 7f

    val scrollFraction = if (config.animated) {
        val infiniteTransition = rememberInfiniteTransition(label = "gridScroll")
        val fraction by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = config.scrollSpeed,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "scrollFraction"
        )
        fraction
    } else {
        0f
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // scrollFraction goes 0→1; we shift by one full gridSpacing per cycle
        val offsetX = -(scrollFraction * gridSpacingPx)
        val offsetY = -(scrollFraction * gridSpacingPx)

        // We need to draw enough symbols to cover the entire canvas plus overflow
        // for the scrolling. Add extra rows/columns on each side.
        val cols = (width / gridSpacingPx).toInt() + 3
        val rows = (height / gridSpacingPx).toInt() + 3

        val paint = android.graphics.Paint().apply {
            color = android.graphics.Color.argb(
                (symbolColor.alpha * 255).toInt(),
                (symbolColor.red * 255).toInt(),
                (symbolColor.green * 255).toInt(),
                (symbolColor.blue * 255).toInt()
            )
            this.strokeWidth = strokeWidth
            strokeCap = android.graphics.Paint.Cap.ROUND
            isAntiAlias = true
        }

        drawContext.canvas.nativeCanvas.apply {
            for (row in -1..rows) {
                for (col in -1..cols) {
                    val cx = col * gridSpacingPx + offsetX
                    val cy = row * gridSpacingPx + offsetY

                    // Horizontal arm of the +
                    drawLine(
                        cx - armLength, cy,
                        cx + armLength, cy,
                        paint
                    )
                    // Vertical arm of the +
                    drawLine(
                        cx, cy - armLength,
                        cx, cy + armLength,
                        paint
                    )
                }
            }
        }
    }
}

/**
 * Renders a full-size animated background of diagonal stripes that scroll
 * upward continuously.
 *
 * The stripes are drawn at a configurable angle using Compose Canvas drawLine,
 * and the vertical scroll offset is animated infinitely.
 */
@Composable
fun DiagonalStripesBackground(
    config: SupraBackground.DiagonalStripes,
    fallbackColor: Color
) {
    val stripeColor = config.stripeColor ?: fallbackColor
    val density = LocalDensity.current

    val stripeWidthPx = with(density) { config.stripeWidth.dp.toPx() }
    val gapWidthPx = with(density) { config.gapWidth.dp.toPx() }
    val period = stripeWidthPx + gapWidthPx

    val scrollFraction = if (config.animated) {
        val infiniteTransition = rememberInfiniteTransition(label = "stripesScroll")
        val fraction by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = config.scrollSpeed,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "stripesScrollFraction"
        )
        fraction
    } else {
        0f
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // The vertical scroll offset moves by one full period per cycle
        val scrollOffsetY = -(scrollFraction * period)

        // Calculate the horizontal run per unit of vertical rise based on the angle.
        // For a 45° angle, tan = 1, so horizontal shift = vertical shift.
        val angleRad = Math.toRadians(config.angle.toDouble())
        val tanAngle = tan(angleRad).toFloat()

        // We need to cover the canvas diagonally. The number of stripes needed
        // depends on the canvas diagonal extent projected onto the perpendicular axis.
        val diagonalExtent = width + height * tanAngle
        val stripeCount = (diagonalExtent / period).toInt() + 3

        for (i in -2..stripeCount) {
            // Base x position for this stripe (perpendicular spacing)
            val baseX = i * period + scrollOffsetY * tanAngle

            // Each stripe is a line from top edge to bottom edge,
            // shifted horizontally by the angle
            val topX = baseX
            val bottomX = baseX - height / tanAngle

            // Draw the stripe at multiple vertical offsets to guarantee full
            // coverage at every point in the scroll cycle (top and bottom).
            for (copy in -2..2) {
                val yShift = copy * period
                val xShift = -copy * period * tanAngle
                drawLine(
                    color = stripeColor,
                    start = Offset(topX + xShift, scrollOffsetY + yShift),
                    end = Offset(bottomX + xShift, height + scrollOffsetY + yShift),
                    strokeWidth = stripeWidthPx
                )
            }
        }
    }
}
