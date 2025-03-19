package com.hoker.supra.presentation.buttons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoker.supra.utils.ModifierUtils.Companion.shadow

@Composable
fun SupraHardwareButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    outerCornerRadius: Dp = 10.dp,
    innerCornerRadius: Dp = 10.dp,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(outerCornerRadius))
            .background(backgroundColor)
            .then(modifier)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize(fraction = 0.8f)
                .shadow(
                    color= Color(0x40FFFFFF),
                    offsetX = (-4).dp,
                    offsetY = (-4).dp,
                    blurRadius = 8.dp
                )
                .shadow(
                    color= Color(0x2E000000),
                    offsetX = (4).dp,
                    offsetY = (4).dp,
                    blurRadius = 8.dp
                )
                .clip(RoundedCornerShape(innerCornerRadius))
                .background(backgroundColor)
        ) {
            RecessedText(
                modifier = Modifier.matchParentSize(),
                text = buttonText
            )
        }
    }
}

@Composable
fun RecessedText(
    modifier: Modifier = Modifier,
    text: String,
    textSize: TextUnit = 24.sp,
    textColor: Color = Color.White,
    // A shadow color with a bit of transparency for subtlety
    shadowColor: Color = Color.Black.copy(alpha = 0.3f)
) {
    // We’re usin’ Canvas so we can draw custom stuff
    Canvas(modifier = modifier) {
        // Convert the textSize to pixels for our paint
        val pxTextSize = textSize.toPx()
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            this.textSize = pxTextSize
            color = textColor.toArgb()
            style = android.graphics.Paint.Style.FILL
        }

        // Create a path for the text
        val textPath = android.graphics.Path().apply {
            // The y-position here is the baseline – adjust if needed
            paint.getTextPath(text, 0, text.length, 0f, pxTextSize, this)
        }

        // Draw the text normally
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawPath(textPath, paint)
        }

        // Now, to get that inner shadow effect:
        drawIntoCanvas { canvas ->
            // Save the current canvas state and clip to the text shape
            val saveCount = canvas.nativeCanvas.save()
            canvas.nativeCanvas.clipPath(textPath)

            // Create a gradient shader that goes from transparent (at the top-left)
            // to a shadow color (toward the bottom-right)
            // This gives the illusion of light comin’ from the top left.
            val shader = android.graphics.LinearGradient(
                0f, 0f,
                size.width, size.height,
                android.graphics.Color.TRANSPARENT,
                shadowColor.toArgb(),
                android.graphics.Shader.TileMode.CLAMP
            )

            // Set up a paint for our shadow overlay
            val shadowPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                this.shader = shader
            }

            // Draw a rectangle over the entire canvas.
            // Because we’re clipped to the text path, this only affects our text.
            canvas.nativeCanvas.drawRect(0f, 0f, size.width, size.height, shadowPaint)

            // Restore the canvas state
            canvas.nativeCanvas.restoreToCount(saveCount)
        }
    }
}