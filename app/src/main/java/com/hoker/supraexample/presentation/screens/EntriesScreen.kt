package com.hoker.supraexample.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.entries.SupraTextField

@Composable
fun EntriesScreen(
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            var text by remember { mutableStateOf("") }
            SupraTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                value = text,
                label = "Enter text",
                onValueChange = { text = it }
            )
        }
    }
}