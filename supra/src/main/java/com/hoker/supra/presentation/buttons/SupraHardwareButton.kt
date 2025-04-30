package com.hoker.supra.presentation.buttons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoker.supra.presentation.text.SupraBodyTextMedium
import com.hoker.supra.utils.ModifierUtils.Companion.shadow

@Composable
fun SupraHardwareButton(
    modifier: Modifier = Modifier,
    text: String,
    outerCornerRadius: Dp = 10.dp,
    innerCornerRadius: Dp = 10.dp,
    backgroundColor: Color,
    textColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(outerCornerRadius))
            .background(backgroundColor)
            .then(modifier)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize(fraction = 0.8f)
                .shadow(
                    color= Color(0x40FFFFFF),
                    offsetX = (-4).dp,
                    offsetY = (-4).dp,
                    blurRadius = 8.dp
                )
                .shadow(
                    color= Color(0x2E000000),
                    offsetX = (4).dp,
                    offsetY = (4).dp,
                    blurRadius = 8.dp
                )
                .clip(RoundedCornerShape(innerCornerRadius))
                .background(backgroundColor)
        ) {
            SupraBodyTextMedium(
                text = text,
                color = textColor
            )
        }
    }
}