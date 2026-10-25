package com.hoker.supraexample.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.hoker.supra.presentation.entries.SupraTextField
import com.hoker.supra.presentation.visual_text_transformations.AsteriskVisualTransformation
import com.hoker.supraexample.presentation.components.ScreenColumn

@Composable
fun EntriesScreen(
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }

    var deviceName by remember { mutableStateOf("Spark 2 · Slot 1") }
    var pin by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    ScreenColumn {
        SupraTextField(
            modifier = Modifier.fillMaxWidth(),
            value = deviceName,
            label = "Device name",
            singleLine = true,
            onValueChange = { deviceName = it }
        )
        SupraTextField(
            modifier = Modifier.fillMaxWidth(),
            value = pin,
            label = "Applet PIN",
            singleLine = true,
            visualTransformation = AsteriskVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            onValueChange = { pin = it.filter(Char::isDigit).take(8) }
        )
        SupraTextField(
            modifier = Modifier.fillMaxWidth(),
            value = notes,
            label = "Slot notes",
            placeholder = "Where this key is used",
            rows = 3,
            onValueChange = { notes = it }
        )
    }
}
