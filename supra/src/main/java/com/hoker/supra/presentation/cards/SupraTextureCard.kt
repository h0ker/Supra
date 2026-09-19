package com.hoker.supra.presentation.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hoker.supra.R
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.theme.ErrorRed
import kotlin.random.Random

enum class TextureType {

    SLATE,

    /** A random topographic mask. */
    TOPOGRAPHIC,

    /** Specific topographic masks. topo1, 3 and 5 are the standard material. */
    TOPO1,
    TOPO3,
    TOPO5;

    fun getTextureId(): Int {
        return when(this) {
            SLATE -> R.drawable.slate
            TOPO1 -> R.drawable.topo1
            TOPO3 -> R.drawable.topo3
            TOPO5 -> R.drawable.topo5
            TOPOGRAPHIC -> {
                val imageIndex = Random.nextInt(1, 7)
                when (imageIndex) {
                    1 -> R.drawable.topo1
                    2 -> R.drawable.topo2
                    3 -> R.drawable.topo3
                    4 -> R.drawable.topo4
                    5 -> R.drawable.topo5
                    else -> R.drawable.topo6
                }
            }
        }
    }
}

fun darkenColor(color: Color, factor: Float): Color{
    val red = (color.red * 255 * (1 - factor)).coerceIn(0f, 255f).toInt()
    val green = (color.green * 255 * (1 - factor)).coerceIn(0f, 255f).toInt()
    val blue = (color.blue * 255 * (1 - factor)).coerceIn(0f, 255f).toInt()
    return Color(red, green, blue)
}

/**
 * Material plate: one tinted texture mask, always. Get presence from [scale], [wear] and [misregister]
 * rather than stacking a second texture.
 *
 * - topo1/3/5 are the standard material; [TextureType.SLATE] is the quieter grain for rows.
 * - Keep [wear] and [misregister] off in list rows; they are for standalone plates.
 * - No specular sweep and no fasteners: they read as anodised metal, which is not the Supra finish.
 *
 * @param scale Blows the mask up past the plate.
 * @param misregister A second, offset print of the same mask: a registration error, not a glow.
 */
@Composable
fun SupraTextureCard(
    modifier: Modifier = Modifier,
    textureType: TextureType = TextureType.TOPOGRAPHIC,
    backgroundColor: Color = Color(0xFF1B2329),
    tint: Color = Color(0xFF2A3841),
    scale: Float = 1f,
    flip: Boolean = false,
    shape: Shape = SupraShapes.material,
    wear: Boolean = false,
    misregister: Boolean = false,
    misregisterColor: Color = ErrorRed,
    content: @Composable BoxScope.() -> Unit
) {
    // Resolved once so a random topo pick stays stable, and the misregistered print reuses the same mask
    val maskId = remember(textureType) { textureType.getTextureId() }

    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(maskId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(tint),
            alpha = if (textureType == TextureType.SLATE) .9f else 1f,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    rotationZ = if (flip) 180f else 0f
                }
        )
        if (misregister) {
            Image(
                painter = painterResource(maskId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(misregisterColor),
                alpha = .32f,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        rotationZ = if (flip) 180f else 0f
                        translationX = 3.dp.toPx()
                        translationY = 2.dp.toPx()
                        blendMode = BlendMode.Screen
                    }
            )
        }
        if (wear) {
            Box(
                Modifier
                    .matchParentSize()
                    .drawBehind {
                        val hairline = 1.dp.toPx()
                        // top edge catching light
                        drawLine(Color.White.copy(alpha = .28f), Offset(0f, hairline / 2), Offset(size.width, hairline / 2), hairline)
                        // grime settling at the bottom
                        val band = 32.dp.toPx()
                        drawRect(
                            Brush.verticalGradient(
                                0f to Color.Transparent,
                                1f to Color.Black.copy(alpha = .5f),
                                startY = size.height - band,
                                endY = size.height
                            )
                        )
                        // worn outer edge
                        drawOutline(
                            outline = shape.createOutline(size, layoutDirection, this),
                            color = Color.Black.copy(alpha = .6f),
                            style = Stroke(width = hairline * 2)
                        )
                    }
            )
        }
        content()
    }
}
