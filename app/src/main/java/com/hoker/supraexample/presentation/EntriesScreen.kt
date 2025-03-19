package com.hoker.supraexample.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun EntriesScreen() {
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

@Composable
fun SupraTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    singleLine: Boolean = false,
    focusedBorderColor: Color = MaterialTheme.colorScheme.secondary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    visualTransformation: VisualTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: (@Composable () -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = singleLine,
        trailingIcon = trailingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor
        ),
        label = { Text(label) },
        onValueChange = { onValueChange(it) },
        visualTransformation = visualTransformation ?: VisualTransformation.None,
        keyboardOptions = keyboardOptions
    )
}