package com.hoker.supraexample.presentation.models

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color
import com.hoker.supra.presentation.theme.BackgroundDark
import com.hoker.supra.presentation.theme.BackgroundPolice
import com.hoker.supra.presentation.theme.ErrorRed
import com.hoker.supra.presentation.theme.OnPrimaryDark
import com.hoker.supra.presentation.theme.OnPrimaryPolice
import com.hoker.supra.presentation.theme.PrimaryDark
import com.hoker.supra.presentation.theme.PrimaryPolice
import com.hoker.supra.presentation.theme.SurfaceDark
import com.hoker.supra.presentation.theme.SurfacePolice
import com.hoker.supra.presentation.theme.TertiaryDark
import com.hoker.supra.presentation.theme.TertiaryPolice
import com.hoker.supra.presentation.theme.UbBackground
import com.hoker.supra.presentation.theme.UbOnPrimary
import com.hoker.supra.presentation.theme.UbPrimary
import com.hoker.supra.presentation.theme.UbSurface
import com.hoker.supra.presentation.theme.UbTertiary
import com.hoker.supra.presentation.theme.VivoKeyBlue

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
    POLICE(
        title = "TX920",
        colorScheme = darkColorScheme(
            surface = UbSurface,
            background = UbBackground,
            tertiary = UbTertiary,
            primary = UbPrimary,
            onPrimary = UbOnPrimary,
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