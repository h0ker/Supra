package com.hoker.supra.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BlurMaskFilter
import android.graphics.Matrix
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import kotlin.random.Random

class ModifierUtils {
    companion object {

        fun Modifier.magneticGlow(
            interference: Float,
            glowColor: Color = Color(0xFF00E5FF)
        ): Modifier = composed {
            // 1. Setup the Infinite Heartbeat
            // We animate a dummy float from 0 to 1 endlessly.
            // We will read this value later to force the UI to redraw.
            val infiniteTransition = rememberInfiniteTransition(label = "noise_ticker")
            val heartbeat by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "noise_tick"
            )

            // 2. Texture Generation (Cached)
            val noiseBrush = remember {
                val size = 512
                val bitmap = createBitmap(size, size)
                val pixels = IntArray(size * size)

                for (i in pixels.indices) {
                    // "Speckle" logic: 30% of pixels get color, others are transparent
                    if (Random.nextFloat() > 0.7f) {
                        val alpha = Random.nextInt(100, 255)
                        pixels[i] = android.graphics.Color.argb(alpha, 255, 255, 255)
                    } else {
                        pixels[i] = android.graphics.Color.TRANSPARENT
                    }
                }
                bitmap.setPixels(pixels, 0, size, 0, 0, size, size)

                val imageShader = ImageShader(bitmap.asImageBitmap(), TileMode.Repeated, TileMode.Repeated)
                val matrix = Matrix()
                matrix.setScale(1.5f, 1.5f) // Scale up for "chunky" analog look
                imageShader.setLocalMatrix(matrix)

                ShaderBrush(imageShader)
            }

            this.drawWithContent {
                drawContent()

                // 3. OPTIMIZATION CHECK
                // If interference is off, we return HERE.
                // Because we haven't read 'heartbeat' yet, Compose stops listening to the animation.
                // This ensures zero battery drain when the effect is inactive.
                if (interference <= 0.01f) return@drawWithContent

                // 4. READ THE HEARTBEAT
                // Simply accessing this variable creates a "read dependency."
                // Compose now knows: "This frame depends on 'heartbeat'. Since 'heartbeat' changes every frame, I must redraw every frame."
                val tick = heartbeat

                val glowHeight = size.height * (interference * 0.5f).coerceAtMost(0.6f)
                val gradientBufferY = glowHeight * 0.85f // Fade out before the edge to avoid lines

                // Draw Glow
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(glowColor.copy(alpha = 0.7f * interference), Color.Transparent),
                        startY = 0f,
                        endY = gradientBufferY
                    ),
                    size = Size(width = size.width, height = glowHeight)
                )

                // 5. Draw Noise (Animated)
                // Since we are redrawing every frame, Random generates a new position every frame
                val noiseOffsetX = Random.nextFloat() * 512f
                val noiseOffsetY = Random.nextFloat() * 512f

                drawIntoCanvas { canvas ->
                    canvas.save()
                    canvas.clipRect(0f, 0f, size.width, glowHeight)
                    canvas.translate(noiseOffsetX, noiseOffsetY)

                    drawRect(
                        brush = noiseBrush,
                        topLeft = Offset(-noiseOffsetX, -noiseOffsetY),
                        size = Size(width = size.width + 512f, height = glowHeight + 512f),
                        alpha = 0.8f * interference,
                        blendMode = BlendMode.Overlay
                    )
                    canvas.restore()
                }
            }
        }

        @SuppressLint("SuspiciousModifierThen")
        fun Modifier.shadow(
            color: Color = Color.Black,
            offsetX: Dp = 0.dp,
            offsetY: Dp = 0.dp,
            blurRadius: Dp = 0.dp
        ) = then(
            drawBehind {
                drawIntoCanvas { canvas ->

                    val paint = Paint()
                    val frameworkPaint = paint.asFrameworkPaint()
                    if (blurRadius!=0.dp){
                        frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
                    }
                    frameworkPaint.color = color.toArgb()

                    val leftPixel = offsetX.toPx()
                    val topPixel = offsetY.toPx()
                    val rightPixel = size.width + topPixel
                    val bottomPixel = size.height + leftPixel

                    canvas.drawRect(
                        left = leftPixel,
                        top = topPixel,
                        right = rightPixel,
                        bottom = bottomPixel,
                        paint = paint
                    )
                }
            }
        )
    }
}