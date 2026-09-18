package com.hoker.supra.presentation.list_items

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoker.supra.presentation.cards.MaterialStyle
import com.hoker.supra.presentation.cards.SupraTextureCard
import com.hoker.supra.presentation.cards.TextureType
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.theme.Ink2
import com.hoker.supra.presentation.theme.Ink3
import com.hoker.supra.presentation.theme.Ink5

/**
 * Textured list row: flat material, row corners, a hairline box and an optional mono status.
 *
 * @param status Short mono status on the right. Use an em dash for "nothing to report", never "Inactive".
 * @param live Marks the row as live: a 2dp accent ring around the whole row, and an accent status. At most one per list.
 */
@Composable
fun SupraTextureListItem(
    modifier: Modifier = Modifier,
    title: String,
    leftIconId: Int? = null,
    rightIconId: Int? = null,
    leftIconImageVector: ImageVector? = null,
    rightIconImageVector: ImageVector? = null,
    textureType: TextureType = TextureType.SLATE,
    backgroundColor: Color = Ink3,
    status: String? = null,
    live: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    SupraTextureCard(
        modifier = modifier
            .clip(SupraShapes.row)
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            }
            // Selection rings the whole object rather than marking one edge, so it survives the row
            // being centred, reordered, or shown on its own
            .border(
                width = if (live) 2.dp else 1.dp,
                color = if (live) MaterialTheme.colorScheme.secondary else Ink2,
                shape = SupraShapes.row
            ),
        material = MaterialStyle.FLAT,
        textureType = textureType,
        backgroundColor = backgroundColor,
        shape = SupraShapes.row
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Sizes.rowInset, end = Sizes.rowInsetTrailing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Sizes.small)
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Sizes.small)
            ) {
                leftIconId?.let { iconId ->
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = "left icon"
                    )
                }
                leftIconImageVector?.let { vector ->
                    Icon(
                        imageVector = vector,
                        contentDescription = "left icon"
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Sizes.small)
            ) {
                status?.let {
                    Text(
                        text = it.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (live) MaterialTheme.colorScheme.secondary else Ink5
                    )
                }
                rightIconId?.let { iconId ->
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = "right icon"
                    )
                }
                rightIconImageVector?.let { vector ->
                    Icon(
                        imageVector = vector,
                        contentDescription = "right icon"
                    )
                }
            }
        }
    }
}
