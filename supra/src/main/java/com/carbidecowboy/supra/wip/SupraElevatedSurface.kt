package com.carbidecowboy.supra.wip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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

@Composable
fun SupraElevatedSurface(
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
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .offset { IntOffset(0, -20) }
                .blur(10.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(Color.White)
        )

        Box(
            Modifier
                .fillMaxSize()
                .offset { IntOffset(0, 20) }
                .blur(10.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(Color.Black.copy(alpha = .3f))
        )

        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            gray100,
                            gray200
                        )
                    )
                )
                .border(
                    width = .08.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color.Black.copy(alpha = .15f)
                        )
                    ),
                    shape = RoundedCornerShape(8.dp)
                ),
        ){
            BasicTextField(value = value, onValueChange = onValueChanged)
        }
    }
}