package com.hoker.supra.presentation.indicators

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.min

typealias Glyph = DrawScope.(Color) -> Unit

object LoadingGlyphs {

    val DiamondXShape: Glyph = { color ->
        rotate(degrees = 45f) {
            val s = min(size.width, size.height)
            val half = s / 2f

            // outer square
            drawRect(
                color = color,
                topLeft = Offset(center.x - half, center.y - half),
                size = Size(s, s),
                style = Stroke(width = 4f)
            )
            // the "X"
            drawLine(
                color = color,
                start = Offset(center.x - half, center.y - half),
                end = Offset(center.x + half, center.y + half),
                strokeWidth = 4f
            )
            drawLine(
                color = color,
                start = Offset(center.x - half, center.y + half),
                end = Offset(center.x + half, center.y - half),
                strokeWidth = 4f
            )
        }
    }

    val CircleCross: Glyph = { color ->
        val r = size.minDimension / 5f
        val d = size.minDimension * .75f
        val c = center

        // center dot
        drawCircle(color=color, radius = r, center = c)
        // up/down/left/right
        listOf(Offset(0f, -d), Offset(0f, +d), Offset(-d, 0f), Offset(+d, 0f))
            .forEach { drawCircle(color=color, radius=r, center=c + it) }
    }

    val FourPlus: Glyph = { color ->
        // size of the canvas
        val s = size.minDimension
        // how far from center to place each plus
        val pad = s * 0.5f
        // half‐length of each plus arm
        val arm = s * 0.3f
        // stroke width for the plus lines
        val stroke = s * 0.1f
        // positions for the four plus‐centers
        val centers = listOf(
            Offset(center.x - pad, center.y - pad),
            Offset(center.x + pad, center.y - pad),
            Offset(center.x - pad, center.y + pad),
            Offset(center.x + pad, center.y + pad),
        )

        centers.forEach { c ->
            // horizontal stroke of the +
            drawLine(
                color       = color,
                start       = Offset(c.x - arm, c.y),
                end         = Offset(c.x + arm, c.y),
                strokeWidth = stroke
            )
            // vertical stroke of the +
            drawLine(
                color       = color,
                start       = Offset(c.x, c.y - arm),
                end         = Offset(c.x, c.y + arm),
                strokeWidth = stroke
            )
        }
    }

    val glyphList = listOf<Glyph>(
        DiamondXShape,
        CircleCross,
        FourPlus
    )
}