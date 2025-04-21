package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hoker.supra.presentation.sizes.Sizes

@Composable
fun SupraFXScreen(
    onToggleLoadingClicked: () -> Unit,
    onFirePulse: (color: Color) -> Unit
) {

    val tertiary = MaterialTheme.colorScheme.tertiary
    val error = MaterialTheme.colorScheme.secondary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Sizes.medium),
        verticalArrangement = Arrangement.spacedBy(Sizes.medium)
    ) {
        Button(
            onClick = {
                onToggleLoadingClicked()
            }
        ) {
            Text(
                text = "Toggle Loading Effect"
            )
        }

        Button(
            onClick = {
                onFirePulse(tertiary)
            }
        ) {
            Text(
                text = "Fire Outgoing Success Pulse"
            )
        }

        Button(
            onClick = {
                onFirePulse(error)
            }
        ) {
            Text(
                text = "Fire Outgoing Error Pulse"
            )
        }
    }
}