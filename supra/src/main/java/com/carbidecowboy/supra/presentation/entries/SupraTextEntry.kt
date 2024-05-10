package com.carbidecowboy.supra.presentation.entries

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.carbidecowboy.supra.utils.ModifierUtils.Companion.innerShadow

@Composable
fun SupraTextEntry(
    modifier: Modifier = Modifier,
    value: String,
    backgroundColor: Color,
    onValueChanged: (String) -> Unit,
) {
    val gray100 = Color(0xffe5e5e5)
    val gray200 = Color(0xffd0d0d0)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gray100)
            .innerShadow(
                shape = CircleShape,
                color = Color.White,
                offsetX = (-2).dp,
                offsetY = (-2).dp,
                blur = 6.dp,
            )
            .innerShadow(
                shape = CircleShape,
                color = Color.Black.copy(0.5f),
                offsetX = 2.dp,
                offsetY = 2.dp,
                blur = 6.dp,
            )
        ,
        contentAlignment = Alignment.Center,

    ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChanged,
                modifier = Modifier
            )
    }
}