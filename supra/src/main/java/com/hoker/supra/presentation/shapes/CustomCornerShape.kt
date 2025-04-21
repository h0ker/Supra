package com.hoker.supra.presentation.shapes

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.unit.Density
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

class CustomCornersShape(
    private val topLeftRadius: Dp,
    private val topRightRadius: Dp,
    private val bottomLeftRadius: Dp,
    private val bottomRightRadius: Dp
) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        with (density) {
            val path = Path().apply {
                val tlRadiusX = topLeftRadius.toPx()
                val tlRadiusY = topLeftRadius.toPx()
                val trRadiusX = topRightRadius.toPx()
                val trRadiusY = topRightRadius.toPx()
                val blRadiusX = bottomLeftRadius.toPx()
                val blRadiusY = bottomLeftRadius.toPx()
                val brRadiusX = bottomRightRadius.toPx()
                val brRadiusY = bottomRightRadius.toPx()

                // Starting point
                moveTo(tlRadiusX, 0f)

                // Top edge and top right corner
                lineTo(size.width - trRadiusX, 0f)
                arcTo(
                    rect = Rect(size.width - 2 * trRadiusX, 0f, size.width, 2 * trRadiusY),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // Right edge and bottom right corner
                lineTo(size.width, size.height - brRadiusY)
                arcTo(
                    rect = Rect(
                        size.width - 2 * brRadiusX,
                        size.height - 2 * brRadiusY,
                        size.width,
                        size.height
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // Bottom edge and bottom left corner
                lineTo(blRadiusX, size.height)
                arcTo(
                    rect = Rect(0f, size.height - 2 * blRadiusY, 2 * blRadiusX, size.height),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // Left edge and top left corner
                lineTo(0f, tlRadiusY)
                arcTo(
                    rect = Rect(0f, 0f, 2 * tlRadiusX, 2 * tlRadiusY),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                close()
            }
            return Outline.Generic(path)
        }
    }
}