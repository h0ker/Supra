package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hoker.supra.presentation.pickers.SupraDropdownPicker
import com.hoker.supraexample.presentation.components.ScreenColumn

@Composable
fun PickersScreen() {
    var slot by remember { mutableStateOf("Slot 1") }
    var algorithm by remember { mutableStateOf("HMAC-256") }

    ScreenColumn {
        SupraDropdownPicker(
            modifier = Modifier
                .fillMaxWidth(.6f),
            items = listOf("Slot 1", "Slot 2"),
            selectedValue = slot,
            label = "Key slot",
            onItemSelected = { slot = it }
        )
        SupraDropdownPicker(
            modifier = Modifier
                .fillMaxWidth(.6f),
            items = listOf("HMAC-SHA1", "HMAC-256", "TOTP"),
            selectedValue = algorithm,
            label = "Algorithm",
            onItemSelected = { algorithm = it }
        )
    }
}
