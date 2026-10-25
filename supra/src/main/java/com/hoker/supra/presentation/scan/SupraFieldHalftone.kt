package com.hoker.supra.presentation.scan

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

/** How long each ring of dots holds before the next one prints. Sets the speed of the sweep. */
private const val STEP_MS = 90
private const val PEAK_ALPHA = 0.65f
private val GridPitch = 9.dp
private val DotRadius = 1.1.dp
private val StartReach = 25.dp
private val EndReach = 420.dp
private val MarkSize = 10.dp

/** One step per grid pitch, so each step reveals roughly the next ring of dots rather than several at once. */
private val StepCount = ceil((EndReach - StartReach) / GridPitch).toInt() + 1

/** Sweep length follows from reach and speed, so extending the reach doesn't slow the rings down. */
private val PeriodMs = StepCount * STEP_MS

/**
 * Default coil position: up from the bottom of the surface, for a bottom-centre NFC antenna.
 *
 * The antenna isn't in the same place on every phone. Where an app can identify the hardware it should
 * pass the real position as `coilY`; this default is the bottom-centre fallback.
 */
val SupraDefaultCoilY = 34.dp

/**
 * Halftone scan field: a printed dot grid revealed outward from the device's NFC coil one ring of dots at a
 * time. It reads as ink and screen tone rather than light, so there is no glow, blur or easing in it.
 * Purely decorative; mount it *below* the content layer.
 *
 * - Dots and coil mark both follow the theme accent, so the field swaps with the theme. Pass
 *   `dotColor = DataBlue` for the machine-readout reading, or Ink5 to make the mark the loud one.
 * - Don't smooth the stepping, and don't raise the alpha, dot size or density: they all spend the same
 *   contrast budget the headline over it depends on.
 * - Keep titles and annotation clear of the lower half. The field occupies all of it, and it clips at
 *   the screen edge by design.
 *
 * @param active False stops the reveal and leaves the coil mark, so the target stays marked. An idle
 *               scan screen that is genuinely listening should always be active.
 * @param coilY Distance up from the bottom of this element to the coil. Grid and mark both derive from
 *              it, so they are concentric by construction.
 * @param markColor Accent while listening or locked; ErrorRed on error.
 */
@Composable
fun SupraFieldHalftone(
    modifier: Modifier = Modifier,
    active: Boolean = true,
    coilY: Dp = SupraDefaultCoilY,
    dotColor: Color = MaterialTheme.colorScheme.secondary,
    markColor: Color = MaterialTheme.colorScheme.secondary
) {
    // Stepped, not smooth: the whole read depends on this being discrete. Animating an Int also means the
    // canvas only redraws when the step changes, once every STEP_MS.
    val step = if (active) {
        rememberInfiniteTransition(label = "halftone").animateValue(
            initialValue = 0,
            targetValue = StepCount,
            typeConverter = Int.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(PeriodMs, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "step"
        ).value.coerceIn(0, StepCount - 1)
    } else {
        -1
    }

    Canvas(modifier.fillMaxSize()) {
        val origin = Offset(size.width / 2f, size.height - coilY.toPx() - MarkSize.toPx() / 2f)

        if (step >= 0) {
            val t = step / (StepCount - 1).toFloat()
            // Grows by exactly one grid pitch per step
            val reach = StartReach.toPx() + step * GridPitch.toPx()
            // Holds its colour through the first part of the sweep, then falls away (1 - t², not linear)
            val color = dotColor.copy(alpha = PEAK_ALPHA * (1f - t * t))
            val pitch = GridPitch.toPx()
            val dot = DotRadius.toPx()
            val span = ceil(reach / pitch).toInt()

            // Tile from the origin, not the layout's top-left, so the dots sit still and only the
            // boundary moves. Anchored to the layout they appear to crawl as the radius grows.
            for (ix in -span..span) {
                for (iy in -span..span) {
                    val p = Offset(origin.x + ix * pitch, origin.y + iy * pitch)
                    if (p.y in 0f..size.height && (p - origin).getDistance() <= reach) {
                        drawCircle(color, dot, p)
                    }
                }
            }
        }

        drawCircle(markColor, MarkSize.toPx() / 2f, origin)
    }
}
