package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.cards.MaterialStyle
import com.hoker.supra.presentation.cards.SupraTextureCard
import com.hoker.supra.presentation.cards.TextureLayer
import com.hoker.supra.presentation.cards.TextureType
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel
import com.hoker.supraexample.presentation.components.Wordmark

// Material layer tints are part of the print, not UI state, so they stay literal
private val customLayers = listOf(
    TextureLayer(TextureType.TOPOGRAPHIC, Color(0xFF33505F), alpha = .8f),
    TextureLayer(TextureType.TOPOGRAPHIC, Color(0xFF7EA0B4), alpha = .35f, scale = 2.1f)
)

@Composable
fun MaterialScreen() {
    ScreenColumn {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Sizes.small)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Sizes.small)
            ) {
                SupraTextureCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    material = MaterialStyle.FLAT
                ) {}
                SpecimenLabel(text = "Flat · one layer")
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Sizes.small)
            ) {
                SupraTextureCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    material = MaterialStyle.HERO
                ) {}
                SpecimenLabel(text = "Hero · slate + topo + wear")
            }
        }

        SupraTextureCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            material = MaterialStyle.HERO
        ) {
            Wordmark(sub = "Hero · misregistered print")
        }

        SupraTextureCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            layers = customLayers,
            wear = true
        ) {
            Wordmark(sub = "Custom stack · two topo layers")
        }
    }
}
