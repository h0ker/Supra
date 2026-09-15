package com.hoker.supra.presentation.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.theme.ErrorRed
import com.hoker.supra.presentation.theme.Ink3
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.Ink5
import com.hoker.supra.utils.ColorUtils.inkOn

enum class SupraButtonTone { ACCENT, NEUTRAL, QUIET, DANGER }

/**
 * The standard Supra button: a low cap on a plate, with the lip of the plate left visible.
 *
 * - One [SupraButtonTone.ACCENT] per screen; the accent means "this commits".
 * - [fullWidth] for the committing action at the bottom of a screen, auto width (88dp min) in a row.
 * - Never set [lip] to 0: the visible lower surface is the whole point.
 * - [squareSize] switches to the original keycap proportions, for moments that mimic real hardware.
 * - Disabled drops to a neutral plate with a dim label, never a dimmed accent.
 */
@Composable
fun SupraHardwareButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    tone: SupraButtonTone = SupraButtonTone.ACCENT,
    backgroundColor: Color? = null,
    textColor: Color? = null,
    height: Dp = 48.dp,
    lip: Dp = 5.dp,
    squareSize: Dp? = null,
    fullWidth: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val plate = when {
        !enabled -> Ink3
        backgroundColor != null -> backgroundColor
        else -> when (tone) {
            SupraButtonTone.ACCENT -> MaterialTheme.colorScheme.secondary
            SupraButtonTone.NEUTRAL -> Ink4
            SupraButtonTone.QUIET -> Ink3
            SupraButtonTone.DANGER -> ErrorRed
        }
    }
    val label = when {
        !enabled -> Ink5
        textColor != null -> textColor
        else -> plate.inkOn()
    }
    val square = squareSize != null

    Box(
        modifier = modifier
            .then(
                when {
                    square -> Modifier.size(squareSize)
                    fullWidth -> Modifier.fillMaxWidth().height(height.coerceAtLeast(44.dp))
                    else -> Modifier.widthIn(min = 88.dp).height(height.coerceAtLeast(44.dp))
                }
            )
            .clip(SupraShapes.capPlate)
            .background(plate)
            .clickable(
                interactionSource = interactionSource,
                indication = null, // no ripple: depth is the only press feedback
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            )
            .padding(lip),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .then(
                    when {
                        square -> Modifier.fillMaxSize(.86f)
                        fullWidth -> Modifier.fillMaxSize()
                        // Wrap the label, but never narrower than the plate's 88dp minimum
                        else -> Modifier.fillMaxHeight().widthIn(min = 88.dp - lip * 2)
                    }
                )
                .capShadow(pressed = pressed && enabled)
                .clip(SupraShapes.cap)
                .background(plate)
                .capInnerShadow(pressed = pressed && enabled)
                .padding(horizontal = if (square) 4.dp else 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = it,
                    contentDescription = null,
                    tint = label
                )
                Spacer(Modifier.width(8.dp))
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

private val capLight = Color.White.copy(alpha = .18f)

/** Resting cap: light up-left, dark down-right. Hidden on press, where the inner pair takes over. */
private fun Modifier.capShadow(pressed: Boolean): Modifier =
    if (pressed) {
        this
    } else {
        this
            .dropShadow(SupraShapes.cap, Shadow(radius = 4.dp, color = capLight, offset = DpOffset((-2).dp, (-2).dp)))
            .dropShadow(SupraShapes.cap, Shadow(radius = 4.dp, color = Color.Black.copy(alpha = .30f), offset = DpOffset(2.dp, 2.dp)))
    }

/** Pressed cap: the same pair drawn inset. */
private fun Modifier.capInnerShadow(pressed: Boolean): Modifier =
    if (pressed) {
        this
            .innerShadow(SupraShapes.cap, Shadow(radius = 4.dp, color = capLight, offset = DpOffset((-2).dp, (-2).dp)))
            .innerShadow(SupraShapes.cap, Shadow(radius = 5.dp, color = Color.Black.copy(alpha = .40f), offset = DpOffset(2.dp, 2.dp)))
    } else {
        this
    }
