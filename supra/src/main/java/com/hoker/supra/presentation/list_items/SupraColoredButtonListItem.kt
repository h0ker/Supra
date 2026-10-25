package com.hoker.supra.presentation.list_items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.Ink7
import com.hoker.supra.utils.ColorUtils.inkOn

enum class ColoredButtonListItemShape {
    /** Control corners with a hairline on the label slab. */
    BLOCK,
    /** The original rounded capsule. */
    CAPSULE
}

/**
 * The loudest row in the system: a bold label slab and an accent cap with a chevron. Once per screen,
 * one or two words (the slab clips rather than wraps).
 */
@Composable
fun SupraColoredButtonListItem(
    modifier: Modifier = Modifier,
    text: String,
    shape: ColoredButtonListItemShape = ColoredButtonListItemShape.BLOCK,
    height: Dp = Sizes.controlHeight,
    width: Dp = Sizes.defaultListItemWidth,
    labelColor: Color = MaterialTheme.colorScheme.surface,
    capColor: Color = MaterialTheme.colorScheme.secondary,
    onClick: () -> Unit,
) {
    val capsule = shape == ColoredButtonListItemShape.CAPSULE
    val radius = if (capsule) height / 2 else 12.dp
    val labelShape = RoundedCornerShape(topStart = radius, bottomStart = radius)
    val capShape = RoundedCornerShape(topEnd = radius, bottomEnd = radius)

    Row(
        modifier = modifier
            .height(height)
            .width(width)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(labelShape)
                .background(labelColor)
                .then(if (capsule) Modifier else Modifier.border(1.dp, Ink4, labelShape))
                .padding(horizontal = Sizes.small),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = if (capsule) 20.sp else 16.sp,
                    letterSpacing = if (capsule) 1.6.sp else 1.28.sp
                ),
                color = Ink7,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
        Box(
            modifier = Modifier
                .width(height)
                .fillMaxHeight()
                .clip(capShape)
                .background(capColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = capColor.inkOn()
            )
        }
    }
}
