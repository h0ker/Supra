package com.hoker.supra.presentation.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.hoker.supra.presentation.sizes.FontSizes

@Composable
fun SupraTitleText(
    modifier: Modifier = Modifier,
    text: String,
    size: TextUnit = FontSizes.large,
    textAlignment: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        fontSize = size,
        textAlign = textAlignment
    )
}