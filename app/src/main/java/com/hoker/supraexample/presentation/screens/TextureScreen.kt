package com.hoker.supraexample.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.cards.SupraTextureCard
import com.hoker.supra.presentation.cards.TextureType
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel
import com.hoker.supraexample.presentation.components.Wordmark

@Composable
fun TextureScreen(
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }

    ScreenColumn {
        SupraTextureCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            textureType = TextureType.TOPOGRAPHIC,
            backgroundColor = MaterialTheme.colorScheme.surface,
            tint = Ink4
        ) {
            Wordmark(sub = "Topographic · 6 variants")
        }

        SupraTextureCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            textureType = TextureType.SLATE,
            backgroundColor = MaterialTheme.colorScheme.surface,
            tint = Ink4
        ) {
            Wordmark(sub = "Slate")
        }

        SupraTextureCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp),
            textureType = TextureType.SLATE,
            backgroundColor = MaterialTheme.colorScheme.surface,
            tint = Ink4,
            shape = SupraShapes.row
        ) {}
        SpecimenLabel(text = "Slate · row corners · list weight")
    }
}
