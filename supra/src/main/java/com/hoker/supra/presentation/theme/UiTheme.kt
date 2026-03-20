package com.hoker.supra.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

enum class UiTheme(
    val title: String,
    val colorScheme: ColorScheme
) {
    DARK(
        title = "Dark",
        colorScheme = darkColorScheme(
            primary = PrimaryDark,
            onPrimary = OnPrimaryDark,
            secondary = VivoKeyBlue,
            tertiary = TertiaryDark,
            background = BackgroundDark,
            surface = SurfaceDark,
            error = ErrorRed
        )
    ),
    VIVOKEY_BLUE(
        title = "VK Blue",
        colorScheme = darkColorScheme(
            primary = VivoKeyBlue,
            onPrimary = OnPrimaryDark,
            secondary = VivoKeyBlue,
            background = PrimaryDark,
            tertiary = TertiaryDark,
            surface = Color(0xFF3C454A),
            error = ErrorRed
        )
    );

    companion object {
        fun fromTitle(title: String?): UiTheme {
            return entries.firstOrNull { it.title == title } ?: VIVOKEY_BLUE
        }
    }
}