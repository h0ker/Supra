package com.hoker.supra.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

/**
 * A theme is an accent swap: every scheme shares the Ink ramp and only [accent] moves.
 * The accent lands in `secondary`, which is reserved for things that express state.
 */
enum class UiTheme(
    val title: String,
    val accent: Color
) {
    LIVE("Live Yellow", AccentLive),
    BLUE("Blue", AccentBlue),
    HI_VIS("Hi-Vis", AccentHiVis),
    BRASS("Brass", AccentBrass);

    val colorScheme: ColorScheme
        get() = darkColorScheme(
            primary = Ink2,
            onPrimary = Ink7,
            secondary = accent,
            onSecondary = Ink0,
            tertiary = DataBlue,
            background = Ink1,
            onBackground = Ink7,
            surface = Ink3,
            onSurface = Ink7,
            error = ErrorRed
        )

    companion object {
        fun fromTitle(title: String?): UiTheme {
            return entries.firstOrNull { it.title == title } ?: LIVE
        }
    }
}
