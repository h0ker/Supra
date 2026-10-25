package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.controls.SupraMultiSelector
import com.hoker.supra.presentation.scan.SupraFieldHalftone
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraDisplayBlock
import com.hoker.supra.presentation.theme.ErrorRed
import com.hoker.supraexample.presentation.components.SpecimenLabel

private enum class ScanState(val title: String) {
    SEARCHING("Searching"),
    LOCKED("Locked"),
    ERROR("Error")
}

@Composable
fun ScanScreen() {
    var state by remember { mutableStateOf(ScanState.SEARCHING) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Layer 1: the field, below everything. It stops on lock or error but the coil mark stays.
        SupraFieldHalftone(
            active = state == ScanState.SEARCHING,
            markColor = if (state == ScanState.ERROR) ErrorRed else MaterialTheme.colorScheme.secondary
        )

        // Layer 2: annotation and controls, top-left and clear of the lower half the field occupies
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(Sizes.medium),
            verticalArrangement = Arrangement.spacedBy(Sizes.tiny)
        ) {
            SpecimenLabel(text = "Halftone · 9px")
            when (state) {
                ScanState.SEARCHING -> SpecimenLabel(text = "Field active")
                ScanState.LOCKED -> SpecimenLabel(text = "Tag locked")
                ScanState.ERROR -> Text(
                    text = "TAG MOVED · READ INCOMPLETE",
                    style = MaterialTheme.typography.labelSmall,
                    color = ErrorRed
                )
            }
            SupraMultiSelector(
                modifier = Modifier
                    .padding(top = Sizes.small)
                    .fillMaxWidth()
                    .height(36.dp),
                options = ScanState.entries.map { it.title },
                selectedOption = state.title,
                onOptionSelect = { title -> state = ScanState.entries.first { it.title == title } }
            )
        }

        // Layer 3: the headline
        SupraDisplayBlock(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = Sizes.medium),
            text = "Scan\nyour\nkey"
        )
    }
}
