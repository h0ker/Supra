package com.carbidecowboy.supraexample.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carbidecowboy.supra.presentation.cards.SupraTextureCard
import com.carbidecowboy.supra.presentation.cards.TextureType

@Composable
fun CardScreen() {
    SupraTextureCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(16.dp),
        textureType = TextureType.TOPOGRAPHIC,
        backgroundColor = Color.Yellow,
    ) { }
}