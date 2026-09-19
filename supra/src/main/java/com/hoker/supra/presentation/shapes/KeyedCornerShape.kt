package com.hoker.supra.presentation.shapes

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

enum class SupraCorner { TopStart, TopEnd, BottomStart, BottomEnd }

/**
 * Rounded on three corners, cut flat at 45° across the fourth. The cut is a polarity mark, like a keyed
 * connector: pick one corner per app and never vary it.
 */
class KeyedCornerShape(
    private val radius: Dp = 12.dp,
    private val cut: Dp = 14.dp,
    private val corner: SupraCorner = SupraCorner.TopStart
) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val r = with(density) { radius.toPx() }
        val c = with(density) { cut.toPx() }
        val rtl = layoutDirection == LayoutDirection.Rtl
        // Start/End follow the layout direction
        val left = when (corner) {
            SupraCorner.TopStart, SupraCorner.BottomStart -> !rtl
            SupraCorner.TopEnd, SupraCorner.BottomEnd -> rtl
        }
        val top = corner == SupraCorner.TopStart || corner == SupraCorner.TopEnd

        val body = Path().apply {
            addRoundRect(RoundRect(0f, 0f, size.width, size.height, CornerRadius(r, r)))
        }
        val x = if (left) 0f else size.width
        val y = if (top) 0f else size.height
        val dx = if (left) c else -c
        val dy = if (top) c else -c
        val wedge = Path().apply {
            moveTo(x, y)
            lineTo(x + dx, y)
            lineTo(x, y + dy)
            close()
        }
        return Outline.Generic(Path().apply { op(body, wedge, PathOperation.Difference) })
    }

    override fun equals(other: Any?): Boolean =
        other is KeyedCornerShape && other.radius == radius && other.cut == cut && other.corner == corner

    override fun hashCode(): Int = (radius.hashCode() * 31 + cut.hashCode()) * 31 + corner.hashCode()
}
