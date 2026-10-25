package com.hoker.supra.presentation.buttons

import android.os.SystemClock
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.theme.ErrorRed
import com.hoker.supra.presentation.theme.Ink3
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.Ink5
import com.hoker.supra.utils.ColorUtils.inkOn
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class SupraButtonTone { ACCENT, NEUTRAL, QUIET, DANGER }

/** Default square icon-only [SupraHardwareButton]. */
val SupraIconButtonSize = 48.dp

/** Roomier icon-only [SupraHardwareButton], for when 48dp reads too small. */
val SupraIconButtonSizeLarge = 56.dp

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
 * - Two canonical sizes: [SupraIconButtonSize] (48dp, default) and [SupraIconButtonSizeLarge] (56dp) for
 *   when 48 reads too small: a screen's single glyph action, a toolbar that should weigh more, gloved or
 *   one-handed use. 44dp is the floor; past 56dp it reads as a tile, not a button. The glyph scales with
 *   the square (46%), so both sizes keep the same optical weight.
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
    size: Dp = SupraIconButtonSize,
    iconSize: Dp = size * 0.46f,
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
    val depth = rememberCapDepth(interactionSource, enabled)

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
                .capShadow(depth = { depth.value }, light = capLight)
                .clip(SupraShapes.cap)
                .background(plate)
                .capInnerShadow(depth = { depth.value }, light = capLight)
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

/** Cap travel into the plate on press. */
private const val PRESS_IN_MS = 70

/** Minimum time the cap stays bottomed out, so even the quickest tap reads as a full keystroke. */
private const val MIN_BOTTOM_MS = 60L

/** Cap springing back out. Slower than the press, like a real key return. */
private const val RELEASE_MS = 160

/**
 * Drives the cap's depth (0 = resting, 1 = fully pressed) from the button's press interactions.
 *
 * Quick taps inside scrollable containers deliver Press and Release in the same frame, so a plain
 * pressed flag never renders. Here a release first lets the press-in finish and dwell for
 * [MIN_BOTTOM_MS], then animates back out, so every tap plays a complete down-and-up cycle while a
 * press-and-hold still stays down for as long as it's held.
 */
@Composable
private fun rememberCapDepth(interactionSource: MutableInteractionSource, enabled: Boolean): Animatable<Float, *> {
    val depth = remember { Animatable(0f) }
    LaunchedEffect(interactionSource, enabled) {
        if (!enabled) {
            depth.snapTo(0f)
            return@LaunchedEffect
        }
        var pressStart = 0L
        var cycle: Job? = null
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    pressStart = SystemClock.uptimeMillis()
                    cycle?.cancel()
                    cycle = launch {
                        depth.animateTo(1f, tween(PRESS_IN_MS, easing = FastOutSlowInEasing))
                    }
                }
                is PressInteraction.Release -> {
                    val pressIn = cycle
                    cycle = launch {
                        // Let the cap bottom out before it comes back up
                        pressIn?.join()
                        val bottomed = SystemClock.uptimeMillis() - pressStart - PRESS_IN_MS
                        if (bottomed < MIN_BOTTOM_MS) delay(MIN_BOTTOM_MS - bottomed)
                        depth.animateTo(0f, tween(RELEASE_MS, easing = FastOutSlowInEasing))
                    }
                }
                is PressInteraction.Cancel -> {
                    // The gesture became a scroll: return without forcing a keystroke
                    cycle?.cancel()
                    cycle = launch { depth.animateTo(0f, tween(RELEASE_MS, easing = FastOutSlowInEasing)) }
                }
            }
        }
    }
    return depth
}

/**
 * Resting cap: light up-left, dark down-right. Fades out as [depth] rises while the inset pair in
 * [capInnerShadow] fades in, so the cap appears to sink rather than switch. Both read [depth] in the
 * draw phase, so the animation redraws the shadows without recomposing the button.
 */
private fun Modifier.capShadow(depth: () -> Float, light: Color): Modifier =
    this
        .dropShadow(SupraShapes.cap) {
            radius = 4.dp.toPx()
            color = light
            offset = Offset((-2).dp.toPx(), (-2).dp.toPx())
            alpha = 1f - depth()
        }
        .dropShadow(SupraShapes.cap) {
            radius = 4.dp.toPx()
            color = Color.Black.copy(alpha = .30f)
            offset = Offset(2.dp.toPx(), 2.dp.toPx())
            alpha = 1f - depth()
        }

/** Pressed cap: the same pair drawn inset, faded in by [depth]. */
private fun Modifier.capInnerShadow(depth: () -> Float, light: Color): Modifier =
    this
        .innerShadow(SupraShapes.cap) {
            radius = 4.dp.toPx()
            color = light
            offset = Offset((-2).dp.toPx(), (-2).dp.toPx())
            alpha = depth()
        }
        .innerShadow(SupraShapes.cap) {
            radius = 5.dp.toPx()
            color = Color.Black.copy(alpha = .40f)
            offset = Offset(2.dp.toPx(), 2.dp.toPx())
            alpha = depth()
        }
