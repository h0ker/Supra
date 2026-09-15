package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hoker.supra.domain.OverlayState
import com.hoker.supra.presentation.buttons.SupraButtonTone
import com.hoker.supra.presentation.buttons.SupraHardwareButton
import com.hoker.supra.presentation.buttons.SupraOutlinedButton
import com.hoker.supra.presentation.indicators.Glyph
import com.hoker.supra.presentation.indicators.LoadingGlyphs
import com.hoker.supra.presentation.indicators.RandomLoadingGlyph
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraBodyTextMedium
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel
import com.hoker.supraexample.presentation.viewmodels.SupraFXViewModel

private val namedGlyphs: List<Pair<String, Glyph>> = listOf(
    "Diamond X" to LoadingGlyphs.DiamondXShape,
    "Circle cross" to LoadingGlyphs.CircleCross,
    "Four plus" to LoadingGlyphs.FourPlus
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IndicatorScreen(
    viewModel: SupraFXViewModel = hiltViewModel()
) {
    ScreenColumn {
        SpecimenLabel(text = "Loading glyphs · active")
        GlyphRow(isLoading = true)
        SpecimenLabel(text = "Loading glyphs · idle")
        GlyphRow(isLoading = false)

        SpecimenLabel(text = "Overlay states")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Sizes.small),
            verticalArrangement = Arrangement.spacedBy(Sizes.small)
        ) {
            SupraHardwareButton(
                text = "Loading",
                tone = SupraButtonTone.NEUTRAL,
                onClick = { viewModel.showOverlay(OverlayState.LOADING_INDETERMINATE) }
            )
            SupraHardwareButton(
                text = "Scan prompt",
                tone = SupraButtonTone.NEUTRAL,
                onClick = { viewModel.showScanPrompt() }
            )
            SupraHardwareButton(
                text = "Scanning",
                tone = SupraButtonTone.NEUTRAL,
                onClick = { viewModel.showOverlay(OverlayState.SCANNING) }
            )
            SupraHardwareButton(
                text = "Prompt + content",
                tone = SupraButtonTone.NEUTRAL,
                onClick = {
                    viewModel.showScanPromptCustomContent {
                        OverlayCard(
                            label = "HMAC-256",
                            message = "Writing key onto slot 1"
                        )
                    }
                }
            )
            SupraHardwareButton(
                text = "Custom content",
                tone = SupraButtonTone.NEUTRAL,
                onClick = {
                    viewModel.showCustomContent {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Sizes.medium)
                        ) {
                            OverlayCard(
                                label = "Slot 2",
                                message = "Key configured · 20 bytes"
                            )
                            SupraOutlinedButton(
                                text = "Dismiss",
                                onClick = { viewModel.dismissOverlay() }
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun GlyphRow(isLoading: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        namedGlyphs.forEach { (name, glyph) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Sizes.medium)
            ) {
                RandomLoadingGlyph(
                    isLoading = isLoading,
                    glyph = glyph
                )
                SpecimenLabel(text = name)
            }
        }
    }
}

@Composable
private fun OverlayCard(
    label: String,
    message: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Sizes.tiny)
    ) {
        SpecimenLabel(text = label)
        SupraBodyTextMedium(
            text = message,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
