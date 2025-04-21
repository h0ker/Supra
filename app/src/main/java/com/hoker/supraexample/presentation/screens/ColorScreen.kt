package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.sizes.Sizes
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ColorScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Sizes.medium),
        verticalArrangement = Arrangement.spacedBy(Sizes.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColorPickerWheel(
            onColorChanged = { color ->

            }
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ColorPickerWheel(
    modifier: Modifier = Modifier,
    onColorChanged: (Color) -> Unit,
    initialColor: Color = Color.Red
) {
    // State for the current selected color.
    var selectedColor by remember { mutableStateOf(initialColor) }
    // Hex input field text.
    var hexInput by remember { mutableStateOf(selectedColor.toHex()) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // The color wheel itself.
        Box(modifier = Modifier.size(300.dp)) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            // Calculate relative to center.
                            val center = size.center
                            val dx = offset.x - center.x
                            val dy = offset.y - center.y
                            val angle = Math.toDegrees(atan2(dy, dx).toDouble()).toFloat()
                            // Convert negative angles to a 0-360 range.
                            val hue = if (angle < 0) angle + 360f else angle
                            // Use full saturation and brightness; adjust if needed.
                            val color = Color.hsv(hue, 1f, 1f)
                            selectedColor = color
                            hexInput = color.toHex()
                            onColorChanged(color)
                        }
                    }
            ) {
                // We'll draw the hue wheel as a series of short arcs.
                val radius = size.minDimension / 2f
                val strokeWidth = radius * 0.3f // Change this to adjust the wheel's thickness.
                for (i in 0 until 360 step 2) {
                    // Draw arc segments for every 2 degrees.
                    val sweepAngle = 2f
                    val startAngle = i.toFloat() - 90f // Shift by 90° so 0 hue is at the top.
                    val arcColor = Color.hsv(i.toFloat(), 1f, 1f)
                    drawArc(
                        color = arcColor,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = Offset(
                            (size.width - 2 * radius) / 2f,
                            (size.height - 2 * radius) / 2f
                        ),
                        size = Size(2 * radius, 2 * radius),
                        style = Stroke(width = strokeWidth)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input for hex color value.
        OutlinedTextField(
            value = hexInput,
            onValueChange = { newHex ->
                hexInput = newHex
                // Validate and update if valid.
                val parsedColor = parseHex(newHex)
                if (parsedColor != null) {
                    selectedColor = parsedColor
                    onColorChanged(parsedColor)
                }
            },
            label = { Text("Hex Color") },
            modifier = Modifier.padding(16.dp)
        )
    }
}

fun Color.toHex(includeAlpha: Boolean = false): String {
    val a = (alpha * 255).roundToInt()
    val r = (red * 255).roundToInt()
    val g = (green * 255).roundToInt()
    val b = (blue * 255).roundToInt()
    return if (includeAlpha) {
        "#%02X%02X%02X%02X".format(a, r, g, b)
    } else {
        "#%02X%02X%02X".format(r, g, b)
    }
}

fun parseHex(hex: String): Color? {
    try {
        var formatted = hex.replace("#", "")
        if (formatted.length == 6) {
            // If no alpha, default to FF (opaque)
            formatted = "FF" + formatted
        }
        if (formatted.length != 8) return null
        val colorInt = formatted.toLong(16).toInt()
        return Color(colorInt)
    } catch (e: Exception) {
        return null
    }
}