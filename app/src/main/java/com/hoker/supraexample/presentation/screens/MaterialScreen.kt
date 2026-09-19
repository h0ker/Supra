package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.cards.SupraTextureCard
import com.hoker.supra.presentation.cards.TextureType
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel
import com.hoker.supraexample.presentation.components.Wordmark

@Composable
fun MaterialScreen() {
    ScreenColumn {
        SpecimenLabel(text = "One mask each · no stacks")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Sizes.small)
        ) {
            MaterialSpecimen(
                modifier = Modifier.weight(1f),
                label = "Slate · row grain"
            ) {
                SupraTextureCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    textureType = TextureType.SLATE,
                    shape = SupraShapes.row
                ) {}
            }
            MaterialSpecimen(
                modifier = Modifier.weight(1f),
                label = "Topo3 · scale 1"
            ) {
                SupraTextureCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    textureType = TextureType.TOPO3
                ) {}
            }
            MaterialSpecimen(
                modifier = Modifier.weight(1f),
                label = "Topo3 · 1.6 · wear · misreg"
            ) {
                SupraTextureCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    textureType = TextureType.TOPO3,
                    scale = 1.6f,
                    wear = true,
                    misregister = true
                ) {}
            }
        }

        SupraTextureCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            textureType = TextureType.TOPO3,
            scale = 1.6f,
            wear = true,
            misregister = true
        ) {
            Wordmark(sub = "Topo3 · scale 1.6 · wear · misregistered print")
        }
    }
}

@Composable
private fun MaterialSpecimen(
    modifier: Modifier = Modifier,
    label: String,
    plate: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Sizes.small)
    ) {
        plate()
        SpecimenLabel(text = label)
    }
}
