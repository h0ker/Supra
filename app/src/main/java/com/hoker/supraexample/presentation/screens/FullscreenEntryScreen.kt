package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.buttons.SupraHardwareButton
import com.hoker.supra.presentation.controls.SupraMultiSelector
import com.hoker.supra.presentation.entries.SupraFullscreenTextField
import com.hoker.supra.presentation.sizes.Sizes
import kotlin.random.Random

private const val KEY_BUDGET_BYTES = 20

private enum class KeyPreset(val title: String, val value: String) {
    EMPTY("Empty", ""),
    TYPED("Typed", "4f2a9c18b077e351"),
    OVER("Over", "4f2a9c18b077e3516d8a9f41")
}

@Composable
fun FullscreenEntryScreen() {
    var preset by remember { mutableStateOf(KeyPreset.EMPTY) }
    var key by remember { mutableStateOf(preset.value) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(Sizes.medium),
        verticalArrangement = Arrangement.spacedBy(Sizes.medium)
    ) {
        SupraMultiSelector(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            options = KeyPreset.entries.map { it.title },
            selectedOption = preset.title,
            onOptionSelect = { title ->
                preset = KeyPreset.entries.first { it.title == title }
                key = preset.value
            }
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            SupraFullscreenTextField(
                value = key,
                label = "HMAC key · hex",
                placeholder = "Paste, scan, or generate",
                byteCount = true,
                maxBytes = KEY_BUDGET_BYTES,
                action = {
                    SupraHardwareButton(
                        text = "Generate random key",
                        fullWidth = true,
                        onClick = {
                            key = (1..KEY_BUDGET_BYTES).joinToString("") { "%x".format(Random.nextInt(16)) }
                        }
                    )
                },
                onValueChange = { key = it }
            )
        }
    }
}
