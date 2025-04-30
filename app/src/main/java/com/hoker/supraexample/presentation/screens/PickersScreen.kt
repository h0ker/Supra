package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.pickers.SupraDropdownPicker
import com.hoker.supra.presentation.sizes.Sizes

@Composable
fun PickersScreen() {
    Column(
        modifier = Modifier
            .padding(Sizes.defaultPadding)
            .fillMaxSize(),
    ) {
        var selectedValue by remember { mutableStateOf("Item 1") }
        SupraDropdownPicker(
            modifier = Modifier
                .fillMaxWidth(.25f)
                .height(64.dp),
            items = listOf("Item 1", "Item 2", "Item 3"),
            selectedValue = selectedValue,
            label = "Example Picker",
            onItemSelected = { selectedValue = it }
        )
    }
}