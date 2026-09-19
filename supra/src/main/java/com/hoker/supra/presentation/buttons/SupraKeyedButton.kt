package com.hoker.supra.presentation.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoker.supra.presentation.shapes.KeyedCornerShape
import com.hoker.supra.presentation.shapes.SupraCorner
import com.hoker.supra.presentation.theme.ErrorRed
import com.hoker.supra.presentation.theme.Ink1
import com.hoker.supra.presentation.theme.Ink3
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.Ink5
import com.hoker.supra.presentation.theme.Ink7
import com.hoker.supra.utils.ColorUtils.inkOn

/**
 * The flat button: no cap, no lip, no depth. One corner cut at 45°, the other three rounded.
 * [SupraHardwareButton] stays the committing action; this is its quiet counterpart for toolbars,
 * dialog footers and dense screens.
 *
 * - The cut is a polarity mark: pick one [corner] per app and never vary it. Top-start points back at
 *   the bracket the screen title sits in.
 * - Press is a tone shift only (an 18% black scrim). No shadow, no travel, no scale, no ripple: depth
 *   belongs to [SupraHardwareButton].
 * - Don't mix keyed and un-keyed flat buttons in one row, or the cut stops meaning anything.
 * - [cut] scales with [height], never with tone: 14dp at 48dp tall.
 */
@Composable
fun SupraKeyedButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    tone: SupraButtonTone = SupraButtonTone.ACCENT,
    outlined: Boolean = false,
    corner: SupraCorner = SupraCorner.TopStart,
    cut: Dp = 14.dp,
    height: Dp = 48.dp,
    fullWidth: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val shape = remember(cut, corner) { KeyedCornerShape(12.dp, cut, corner) }
    val accent = MaterialTheme.colorScheme.secondary
    val fill = when {
        !enabled -> Ink3
        else -> when (tone) {
            SupraButtonTone.ACCENT -> accent
            SupraButtonTone.NEUTRAL -> Ink4
            SupraButtonTone.QUIET -> Ink3
            SupraButtonTone.DANGER -> ErrorRed
        }
    }
    val label = when {
        !enabled -> Ink5
        outlined -> if (tone == SupraButtonTone.ACCENT) accent else Ink7
        else -> fill.inkOn()
    }
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    // A border can't follow the cut, so outlined mode draws the stroke as a clipped backplate with the
    // inset on the backplate's own padding, and clips the face again inside it
    Box(
        modifier = modifier
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier.widthIn(min = 88.dp))
            .height(height)
            .alpha(if (enabled) 1f else .6f)
            .clip(shape)
            .background(fill)
            .then(if (outlined) Modifier.padding(1.5.dp).clip(shape).background(Ink1) else Modifier)
            .drawWithContent {
                drawContent()
                if (pressed) drawRect(Color.Black.copy(alpha = .18f))
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier)
                .fillMaxHeight()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = it,
                    contentDescription = null,
                    tint = label
                )
            }
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    letterSpacing = 1.2.sp
                ),
                color = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
