package com.carbidecowboy.supraexample.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carbidecowboy.supra.presentation.entries.SupraTextEntry

@Composable
fun EntryScreen() {

    var entry1Content by remember { mutableStateOf("") }

    Row (
      modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
    ) {
        SupraTextEntry(
            modifier = Modifier.height(80.dp),
            backgroundColor = Color.Gray,
            value = entry1Content,
            onValueChanged = { text ->
                entry1Content = text
            }
        )
    }
}