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
    TOPOGRAPHIC;

    fun getTextureId(): Int {
        return when(this) {
            SLATE -> {
                R.drawable.slate
            }
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

/**
 * One printed layer of a material stack.
 */
data class TextureLayer(
    val texture: TextureType,
    val tint: Color,
    val alpha: Float = 1f,
    val scale: Float = 1f,
    val offset: Offset = Offset.Zero,
    val flip: Boolean = false
)

/**
 * [HERO] surfaces get the full stack (layers, wear, misregistration). No specular sweep or fasteners:
 * those read as anodised metal, which is not the Supra finish.
 * List rows and repeated cards stay [FLAT]; a column of hero plates fights itself.
 */
enum class MaterialStyle { FLAT, HERO }

private val heroLayers = listOf(
    TextureLayer(TextureType.SLATE, Color(0xFF3E5666), alpha = .85f),
    TextureLayer(TextureType.TOPOGRAPHIC, Color(0xFF6E90A8), alpha = .55f, scale = 1.6f)
)

fun darkenColor(color: Color, factor: Float): Color{
    val red = (color.red * 255 * (1 - factor)).coerceIn(0f, 255f).toInt()
    val green = (color.green * 255 * (1 - factor)).coerceIn(0f, 255f).toInt()
    val blue = (color.blue * 255 * (1 - factor)).coerceIn(0f, 255f).toInt()
    return Color(red, green, blue)
}

/**
 * @param textureType Texture for the single [MaterialStyle.FLAT] layer. Ignored when [layers] is set
 *                    or [material] is [MaterialStyle.HERO].
 * @param tint Tint for the single [MaterialStyle.FLAT] layer.
 * @param layers Explicit layer stack, drawn bottom to top. Overrides [material]'s default stack.
 */
@Composable
fun SupraTextureCard(
    modifier: Modifier = Modifier,
    material: MaterialStyle = MaterialStyle.FLAT,
    textureType: TextureType = TextureType.TOPOGRAPHIC,
    backgroundColor: Color = Color(0xFF1B2329),
    tint: Color = darkenColor(backgroundColor, .2f),
    layers: List<TextureLayer>? = null,
    shape: Shape = SupraShapes.material,
    wear: Boolean = material == MaterialStyle.HERO,
    misregister: Boolean = material == MaterialStyle.HERO,
    misregisterColor: Color = ErrorRed,
    content: @Composable BoxScope.() -> Unit
) {
    val flatFlip = remember { Random.nextBoolean() }
    val stack = layers ?: when (material) {
        MaterialStyle.HERO -> heroLayers
        MaterialStyle.FLAT -> listOf(TextureLayer(textureType, tint, flip = flatFlip))
    }
    // Resolve once so a random topo pick stays stable, and the misregistered
    // plate reuses the exact image of the layer it is offset from.
    val textureIds = remember(stack) { stack.map { it.texture.getTextureId() } }

    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        stack.forEachIndexed { index, layer ->
            Image(
                painter = painterResource(textureIds[index]),
                contentDescription = null,
                colorFilter = ColorFilter.tint(layer.tint),
                alpha = layer.alpha,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        scaleX = layer.scale
                        scaleY = layer.scale
                        rotationZ = if (layer.flip) 180f else 0f
                        translationX = layer.offset.x
                        translationY = layer.offset.y
                    }
            )
        }
        if (misregister && stack.isNotEmpty()) {
            val top = stack.last()
            Image(
                painter = painterResource(textureIds.last()),
                contentDescription = null,
                colorFilter = ColorFilter.tint(misregisterColor),
                alpha = .32f,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        scaleX = top.scale
                        scaleY = top.scale
                        rotationZ = if (top.flip) 180f else 0f
                        translationX = top.offset.x + 3.dp.toPx()
                        translationY = top.offset.y + 2.dp.toPx()
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

