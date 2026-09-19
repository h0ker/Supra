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
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
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
    HardwareButton(
        modifier = modifier,
        text = text,
        icon = icon,
        iconSize = 20.dp,
        contentDescription = null,
        tone = tone,
        backgroundColor = backgroundColor,
        textColor = textColor,
        height = height,
        lip = lip,
        squareSize = squareSize,
        fullWidth = fullWidth,
        enabled = enabled,
        onClick = onClick
    )
}

/**
 * Icon-only [SupraHardwareButton]: a square the height of a standard button (48×48), glyph only, no
 * label and no 88dp minimum. Lip, cap and press inversion match the labelled button, so the two sit in
 * one row without reading as different controls.
 *
 * - [contentDescription] is required: the glyph is the only content, and an unlabelled icon button is a
 *   TalkBack dead end.
 * - 48dp is the size and 44dp the floor. Don't go smaller to fit more of them in.
 * - Use [SupraButtonTone.QUIET] or [SupraButtonTone.NEUTRAL] in groups; a row of accent icon buttons
 *   leaves no primary action.
 * - For a whole toolbar of controls use [SupraKeyedButton] instead. This is for one or two physical
 *   actions sitting next to content.
 */
@Composable
fun SupraHardwareButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String,
    tone: SupraButtonTone = SupraButtonTone.ACCENT,
    backgroundColor: Color? = null,
    iconColor: Color? = null,
    size: Dp = 48.dp,
    iconSize: Dp = 22.dp,
    lip: Dp = 5.dp,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    HardwareButton(
        modifier = modifier,
        text = null,
        icon = icon,
        iconSize = iconSize,
        contentDescription = contentDescription,
        tone = tone,
        backgroundColor = backgroundColor,
        textColor = iconColor,
        height = size,
        lip = lip,
        squareSize = null,
        fullWidth = false,
        enabled = enabled,
        onClick = onClick
    )
}

/**
 * Shared body. A null [text] makes an icon-only button: a height × height square whose cap fills the
 * plate like a labelled one (the [squareSize] keycap is the one that insets its cap).
 */
@Composable
private fun HardwareButton(
    modifier: Modifier,
    text: String?,
    icon: ImageVector?,
    iconSize: Dp,
    contentDescription: String?,
    tone: SupraButtonTone,
    backgroundColor: Color?,
    textColor: Color?,
    height: Dp,
    lip: Dp,
    squareSize: Dp?,
    fullWidth: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val iconOnly = text == null
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
    val capLight = remember(plate) { capLightFor(plate) }

    Box(
        modifier = modifier
            .then(
                when {
                    square -> Modifier.size(squareSize)
                    iconOnly -> Modifier.size(height.coerceAtLeast(44.dp))
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
            .then(
                if (contentDescription != null) {
                    Modifier.semantics { this.contentDescription = contentDescription }
                } else {
                    Modifier
                }
            )
            .padding(lip),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .then(
                    when {
                        square -> Modifier.fillMaxSize(.86f)
                        // An icon button's cap fills the plate like a labelled one
                        fullWidth || iconOnly -> Modifier.fillMaxSize()
                        // Wrap the label, but never narrower than the plate's 88dp minimum
                        else -> Modifier.fillMaxHeight().widthIn(min = 88.dp - lip * 2)
                    }
                )
                .capShadow(pressed = pressed && enabled, light = capLight)
                .clip(SupraShapes.cap)
                .background(plate)
                .capInnerShadow(pressed = pressed && enabled, light = capLight)
                .padding(
                    horizontal = when {
                        iconOnly -> 0.dp
                        square -> 4.dp
                        else -> 16.dp
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = it,
                    contentDescription = null,
                    tint = label
                )
                if (!iconOnly) Spacer(Modifier.width(8.dp))
            }
            text?.let {
                Text(
                    text = it.uppercase(),
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
}

private const val CAP_LIGHT_MIN_ALPHA = .06f
private const val CAP_LIGHT_MAX_ALPHA = .18f

/**
 * The up-left highlight, scaled by the plate's perceptual lightness (CIE L*). A fixed white overlay
 * reads as a strong glint on near-black plates and barely registers on bright ones, so dark plates get
 * a softer light and bright plates keep the full 18%.
 */
private fun capLightFor(plate: Color): Color {
    val y = plate.luminance()
    val lStar = if (y > 0.008856f) 116f * Math.cbrt(y.toDouble()).toFloat() - 16f else 903.3f * y
    val alpha = lerp(CAP_LIGHT_MIN_ALPHA, CAP_LIGHT_MAX_ALPHA, (lStar / 100f).coerceIn(0f, 1f))
    return Color.White.copy(alpha = alpha)
}

/** Resting cap: light up-left, dark down-right. Hidden on press, where the inner pair takes over. */
private fun Modifier.capShadow(pressed: Boolean, light: Color): Modifier =
    if (pressed) {
        this
    } else {
        this
            .dropShadow(SupraShapes.cap, Shadow(radius = 4.dp, color = light, offset = DpOffset((-2).dp, (-2).dp)))
            .dropShadow(SupraShapes.cap, Shadow(radius = 4.dp, color = Color.Black.copy(alpha = .30f), offset = DpOffset(2.dp, 2.dp)))
    }

/** Pressed cap: the same pair drawn inset. */
private fun Modifier.capInnerShadow(pressed: Boolean, light: Color): Modifier =
    if (pressed) {
        this
            .innerShadow(SupraShapes.cap, Shadow(radius = 4.dp, color = light, offset = DpOffset((-2).dp, (-2).dp)))
            .innerShadow(SupraShapes.cap, Shadow(radius = 5.dp, color = Color.Black.copy(alpha = .40f), offset = DpOffset(2.dp, 2.dp)))
    } else {
        this
    }
