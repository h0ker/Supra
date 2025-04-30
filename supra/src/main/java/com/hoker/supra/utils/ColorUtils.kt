package com.hoker.supra.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

object ColorUtils {

    fun Color.contrastingTextColor(): Color {
        return if (luminance() > 0.5f) Color.Companion.Black else Color.Companion.White
    }
}