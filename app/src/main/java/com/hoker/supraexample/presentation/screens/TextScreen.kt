package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraBodyTextLarge
import com.hoker.supra.presentation.text.SupraBodyTextMedium
import com.hoker.supra.presentation.text.SupraBodyTextSmall
import com.hoker.supra.presentation.text.SupraDataGutter
import com.hoker.supra.presentation.text.SupraDisplayBlock
import com.hoker.supra.presentation.text.SupraTitleTextLarge
import com.hoker.supra.presentation.text.SupraTitleTextMedium
import com.hoker.supra.presentation.text.SupraTitleTextSmall
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel

@Composable
fun TextScreen() {
    ScreenColumn {
        Specimen(style = "displayLarge · SupraDisplayBlock") {
            SupraDisplayBlock(text = "Scan\nyour key")
        }
        Specimen(style = "labelSmall + labelMedium · SupraDataGutter") {
            SupraDataGutter(
                modifier = Modifier.fillMaxWidth(),
                width = 240.dp,
                divider = false,
                rows = listOf(
                    "UID" to "04A29F3C1180",
                    "Alg" to "HMAC-256"
                )
            )
        }
        Specimen(style = "titleLarge · SupraTitleTextLarge") {
            SupraTitleTextLarge(text = "Spark 2")
        }
        Specimen(style = "titleMedium · SupraTitleTextMedium") {
            SupraTitleTextMedium(text = "Key slot 1")
        }
        Specimen(style = "titleSmall · SupraTitleTextSmall") {
            SupraTitleTextSmall(text = "Write key to slot")
        }
        Specimen(style = "bodyLarge · SupraBodyTextLarge") {
            SupraBodyTextLarge(text = "Hold still")
        }
        Specimen(style = "bodyMedium · SupraBodyTextMedium") {
            SupraBodyTextMedium(text = "Hold your device to the back of the phone")
        }
        Specimen(style = "bodySmall · SupraBodyTextSmall") {
            SupraBodyTextSmall(text = "The key is written once and cannot be read back")
        }
    }
}

@Composable
private fun Specimen(
    style: String,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Sizes.tiny)
    ) {
        SpecimenLabel(text = style)
        content()
    }
}
