package com.hoker.supra.presentation.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.text.SupraBodyTextMedium

@Composable
fun SupraOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    color: Color = MaterialTheme.colorScheme.onPrimary
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, color),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = color,
            containerColor = Color.Transparent
        )
    ) {
        SupraBodyTextMedium(
            text = text
        )
    }
}