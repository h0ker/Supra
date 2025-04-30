package com.hoker.supra.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hoker.supra.R
import com.hoker.supra.presentation.sizes.FontSizes

val Univers = FontFamily(
    Font(R.font.univers_light, FontWeight.Light),
    Font(R.font.univers_regular, FontWeight.Normal),
    Font(R.font.univers_bold, FontWeight.Bold)
)

val NBInternational = FontFamily(
    Font(R.font.nb_international_pro_mono, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    //bodyText
    bodySmall = TextStyle(
        fontFamily = Univers,
        fontWeight = FontWeight.Normal,
        fontSize = FontSizes.small
    ),
    bodyMedium = TextStyle(
        fontFamily = Univers,
        fontWeight = FontWeight.Normal,
        fontSize = FontSizes.standard,
    ),
    bodyLarge = TextStyle(
        fontFamily = Univers,
        fontWeight = FontWeight.Normal,
        fontSize = FontSizes.large
    ),
    //Title text
    titleSmall = TextStyle(
        fontFamily = Univers,
        fontWeight = FontWeight.Light,
        fontSize = FontSizes.titleSmall
    ),
    titleMedium = TextStyle(
        fontFamily = Univers,
        fontWeight = FontWeight.Light,
        fontSize = FontSizes.titleMedium
    ),
    titleLarge = TextStyle(
        fontFamily = Univers,
        fontWeight = FontWeight.Light,
        fontSize = FontSizes.titleLarge
    )
)