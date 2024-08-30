package com.hoker.supra.presentation.cards

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hoker.supra.R
import kotlin.random.Random

enum class TextureType {

    TOPOGRAPHIC;

    fun getTextureId(): Int {
        return when(this) {
            TOPOGRAPHIC -> {
                val imageIndex = Random.nextInt(6)
                when (imageIndex) {
                    1 -> R.drawable.topo1
                    2 -> R.drawable.topo2
                    3 -> R.drawable.topo3
                    4 -> R.drawable.topo4
                    5 -> R.drawable.topo5
                    6 -> R.drawable.topo6
                    else -> R.drawable.topo1
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

@Composable
fun SupraTextureCard(
    modifier: Modifier = Modifier,
    textureType: TextureType,
    backgroundColor: Color,
    tint: Color = darkenColor(backgroundColor, .2f),
    content: @Composable () -> Unit
) {
    // Random rotation angle
    val rotationAngle = remember { Random.nextInt(2) * 180f }
    val backgroundId = remember { textureType.getTextureId() }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(backgroundId),
            contentDescription = "Tinted and Rotated Square Image",
            colorFilter = ColorFilter.tint(tint),
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = rotationAngle
                }
                .matchParentSize(),
            contentScale = ContentScale.Crop
        )
        content()
    }
}