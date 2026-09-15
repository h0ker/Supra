package com.hoker.supra.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.hoker.supra.presentation.theme.Ink0
import com.hoker.supra.presentation.theme.Ink7

object ColorUtils {

    fun Color.contrastingTextColor(): Color {
        return if (luminance() > 0.5f) Color.Companion.Black else Color.Companion.White
    }

    /**
     * Label colour for text sitting on a filled plate: Ink0 or Ink7, whichever has the higher contrast
     * ratio. Accent swaps range from Live Yellow to a dark Blue, so a fixed label colour can't work.
     */
    fun Color.inkOn(): Color {
        val lum = luminance()
        val darkContrast = (lum + 0.05f) / (Ink0.luminance() + 0.05f)
        val lightContrast = (Ink7.luminance() + 0.05f) / (lum + 0.05f)
        return if (darkContrast >= lightContrast) Ink0 else Ink7
    }
}
