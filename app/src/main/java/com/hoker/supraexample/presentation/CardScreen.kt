package com.hoker.supraexample.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoker.supra.presentation.cards.SupraTextureCard
import com.hoker.supra.presentation.cards.TextureType

@Composable
fun CardScreen(
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }

    val apexFont = FontFamily(Font(com.hoker.supra.R.font.apex_font))
    val universFont = FontFamily(Font(com.hoker.supra.R.font.univers_light))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SupraTextureCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(16.dp),
            textureType = TextureType.TOPOGRAPHIC,
            backgroundColor = Color(0xFF293239),
            tint = Color(0xFF42667E)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SUPRA",
                    color = Color.White,
                    fontFamily = apexFont,
                    fontSize = 64.sp
                )
                Text(
                    text = "Design System",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = universFont
                )
            }
        }

        SupraTextureCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(16.dp),
            textureType = TextureType.SLATE,
            backgroundColor = Color(0xFF293239),
            tint = Color(0xFF42667E)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SUPRA",
                    color = Color.White,
                    fontFamily = apexFont,
                    fontSize = 64.sp
                )
                Text(
                    text = "Design System",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = universFont
                )
            }
        }
    }
}