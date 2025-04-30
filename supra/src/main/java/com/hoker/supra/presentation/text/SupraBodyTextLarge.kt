package com.hoker.supra.presentation.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SupraBodyTextLarge(
    modifier: Modifier = Modifier,
    text: String,
    textAlignment: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = textAlignment,
        color = color
    )
}