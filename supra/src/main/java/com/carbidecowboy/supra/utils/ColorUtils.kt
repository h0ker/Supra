package com.carbidecowboy.supra.utils

import androidx.compose.ui.graphics.Color

class ColorUtils {
    companion object {
        fun darkenColor(color: Color, factor: Float): Color {
            val red = (color.red * 255 * (1 - factor)).coerceIn(0f, 255f).toInt()
            val green = (color.green * 255 * (1 - factor)).coerceIn(0f, 255f).toInt()
            val blue = (color.blue * 255 * (1 - factor)).coerceIn(0f, 255f).toInt()
            return Color(red, green, blue)
        }

        fun lightenColor(color: Color, factor: Float): Color {
            val red = (color.red * 255 * (1 + factor)).coerceIn(0f, 255f).toInt()
            val green = (color.green * 255 * (1 + factor)).coerceIn(0f, 255f).toInt()
            val blue = (color.blue * 255 * (1 + factor)).coerceIn(0f, 255f).toInt()
            return Color(red, green, blue)
        }
    }
}