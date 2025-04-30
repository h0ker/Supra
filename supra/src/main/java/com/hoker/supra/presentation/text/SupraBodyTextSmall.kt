package com.hoker.supra.presentation.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun SupraBodyTextSmall(
    modifier: Modifier = Modifier,
    text: String,
    textAlignment: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.primary,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodySmall,
        textAlign = textAlignment,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}