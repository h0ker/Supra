package com.carbidecowboy.supra.presentation.surface

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.carbidecowboy.supra.utils.ColorUtils.Companion.darkenColor
import com.carbidecowboy.supra.utils.ColorUtils.Companion.lightenColor
import com.carbidecowboy.supra.utils.ModifierUtils.Companion.shadow

@Composable
fun SupraConvexSurface(
    modifier : Modifier = Modifier,
    backgroundColor: Color,
    cornerRadius: Dp = 8.dp,
    content : @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .shadow(
                    color= lightenColor(backgroundColor, .2f),
                    offsetX = (-4).dp,
                    offsetY = (-4).dp,
                    blurRadius = 15.dp
                )
                .shadow(
                    color= Color(0x2E000000),
                    offsetX = 4.dp,
                    offsetY = 4.dp,
                    blurRadius = 8.dp
                )
            .clip(RoundedCornerShape(cornerRadius)),
           color = backgroundColor
        ) {
            content()
        }
    }
}
