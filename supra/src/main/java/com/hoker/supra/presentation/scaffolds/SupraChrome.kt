package com.hoker.supra.presentation.scaffolds

import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.Ink5
import com.hoker.supra.presentation.theme.Ink7
import kotlin.math.roundToInt
import kotlin.math.sqrt

object SupraChromeDefaults {
    val BracketSize = 16.dp
    val BracketWeight = 2.dp

    /** The bracket's own bend, so the brackets sit in the rounded radius scale. */
    val BracketBend = 6.dp

    /** Left and right inset. Constant, never derived. Also the floor for the bottom inset. */
    val BracketInset = 14.dp

    /** Top inset. Edge-to-edge layouts already get room from the status bar, so the top brackets hug it. */
    val BracketInsetTop = 4.dp

    /** Horizontal padding that keeps corner and rail content clear of the bracket arms. */
    val ReadoutGutter = BracketSize + 8.dp
}

/** Extra room so the 2dp stroke itself clears the display's curve, not just the bracket's corner point. */
private val StrokeClearance = 3.dp

/**
 * The bottom inset at which a bracket corner sitting [inset] from the side clears a round display corner.
 *
 * The bracket shape and the horizontal inset never change: only the bottom gives. With the sides pinned at
 * `d`, a corner at `(d, y)` from the display corner is inside the glass when `(R - d)² + (R - y)² ≤ R²`,
 * so `y ≥ R - √(R² - (R - d)²)`. Floored at [inset], so a flat-cornered device keeps the tight bottom.
 *
 * Drive the bezel's bottom padding from this too, or the content stays put while the brackets move down
 * and the gutter between them collapses.
 */
@Composable
fun rememberBracketInsetBottom(inset: Dp = SupraChromeDefaults.BracketInset): Dp {
    val view = LocalView.current
    val density = LocalDensity.current
    // Root insets arrive after the first frame; keying on the system bar inset re-reads them once they land
    val systemBarsBottom = WindowInsets.systemBars.getBottom(density)
    return remember(view, density, inset, systemBarsBottom) {
        val insets = view.rootWindowInsets
        val radiusPx = listOf(
            RoundedCorner.POSITION_BOTTOM_LEFT,
            RoundedCorner.POSITION_BOTTOM_RIGHT
        ).maxOf { insets?.getRoundedCorner(it)?.radius ?: 0 }
        val r = with(density) { radiusPx.toDp() }
        val k = (r - inset).coerceAtLeast(0.dp)
        val inner = sqrt((r.value * r.value - k.value * k.value).coerceAtLeast(0f)).dp
        maxOf(inset, r - inner + StrokeClearance)
    }
}

/**
 * Arc corner brackets drawn above the content. The bracket colour is the accent; brackets are state
 * furniture, so they are one of the few places the accent is allowed.
 *
 * @param bend Radius of the quarter-arc joining the two arms. Square brackets read as a leftover hard corner.
 * @param inset Left and right inset. Constant on every device.
 * @param insetBottom Bottom inset, from [rememberBracketInsetBottom] on displays with round corners.
 */
fun Modifier.supraBrackets(
    color: Color,
    size: Dp = SupraChromeDefaults.BracketSize,
    weight: Dp = SupraChromeDefaults.BracketWeight,
    bend: Dp = SupraChromeDefaults.BracketBend,
    inset: Dp = SupraChromeDefaults.BracketInset,
    insetTop: Dp = SupraChromeDefaults.BracketInsetTop,
    insetBottom: Dp = SupraChromeDefaults.BracketInset
) = drawWithContent {
    drawContent()
    val s = size.toPx()
    val w = weight.toPx()
    val r = bend.toPx().coerceAtMost(s)
    // Offset the corner by half the stroke so the outer edge of each arm sits on the inset line
    val left = inset.toPx() + w / 2f
    val top = insetTop.toPx() + w / 2f
    val right = this.size.width - left
    val bottom = this.size.height - (insetBottom.toPx() + w / 2f)
    val stroke = Stroke(width = w, cap = StrokeCap.Round, join = StrokeJoin.Round)
    listOf(
        Offset(left, top) to Offset(1f, 1f),
        Offset(right, top) to Offset(-1f, 1f),
        Offset(left, bottom) to Offset(1f, -1f),
        Offset(right, bottom) to Offset(-1f, -1f)
    ).forEach { (corner, dir) ->
        drawPath(bracketPath(corner, dir, s, r), color, style = stroke)
    }
}

/**
 * One bracket: an arm along each edge from [corner], joined by a quarter-arc of radius [r].
 * [dir] points from the corner into the screen on each axis.
 */
private fun bracketPath(corner: Offset, dir: Offset, s: Float, r: Float): Path {
    // Control-point factor for a cubic approximation of a quarter circle
    val k = 0.5523f
    return Path().apply {
        moveTo(corner.x, corner.y + dir.y * s)
        lineTo(corner.x, corner.y + dir.y * r)
        cubicTo(
            corner.x, corner.y + dir.y * r * (1 - k),
            corner.x + dir.x * r * (1 - k), corner.y,
            corner.x + dir.x * r, corner.y
        )
        lineTo(corner.x + dir.x * s, corner.y)
    }
}

/**
 * Bracket-corner furniture: the current screen name tucked into the top-left bracket and an index in
 * the top-right. Place it at the top bracket inset; its own padding clears the arms and tucks the
 * text inside the corner. [index] must be genuine data (index, count, revision, mode).
 */
@Composable
fun SupraBracketTitle(
    modifier: Modifier = Modifier,
    title: String?,
    index: String?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = SupraChromeDefaults.BracketInsetTop + 6.dp,
                start = SupraChromeDefaults.ReadoutGutter,
                end = SupraChromeDefaults.ReadoutGutter
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title.orEmpty().uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp, letterSpacing = 1.8.sp),
            color = Ink7,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        index?.let {
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = it.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = Ink5,
                maxLines = 1
            )
        }
    }
}

/**
 * Registration rail: a row of hairline ticks with a major tick every sixth, and an optional stamp.
 *
 * Pass [level] to drive it as a signal meter: ticks light left to right in the accent, with a glowing head.
 * Only ever drive it from a real reading; a rail animating on a timer is decoration.
 *
 * @param level 0f..1f. Null keeps the static registration rail.
 * @param label Optional text that replaces [stamp]. By default a live meter keeps the revision stamp: the ticks carry the reading.
 */
@Composable
fun SupraRail(
    modifier: Modifier = Modifier,
    stamp: String? = null,
    ticks: Int = 42,
    level: Float? = null,
    label: String? = null
) {
    val accent = MaterialTheme.colorScheme.secondary
    val lit = level?.let { (it.coerceIn(0f, 1f) * (ticks - 1)).roundToInt() } ?: -1
    val tickShape = RoundedCornerShape(1.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = SupraChromeDefaults.ReadoutGutter,
                end = SupraChromeDefaults.ReadoutGutter,
                top = 8.dp,
                // Mirrors the corner title's top inset, so the rail nestles inside the bottom brackets
                bottom = SupraChromeDefaults.BracketInsetTop + 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(ticks) { i ->
            val major = i % 6 == 0
            val on = i <= lit
            val head = on && i == lit
            Box(
                Modifier
                    .padding(end = 3.dp)
                    .width(1.dp)
                    .height(if (major || head) 9.dp else 4.dp)
                    .then(if (head) Modifier.dropShadow(tickShape, Shadow(radius = 6.dp, color = accent)) else Modifier)
                    .background(
                        color = when {
                            on -> accent
                            major -> Ink5
                            else -> Ink4
                        },
                        shape = tickShape
                    )
            )
        }
        (label ?: stamp)?.let {
            Spacer(Modifier.weight(1f))
            Text(
                text = it.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = if (label != null) accent else Ink5,
                maxLines = 1
            )
        }
    }
}
