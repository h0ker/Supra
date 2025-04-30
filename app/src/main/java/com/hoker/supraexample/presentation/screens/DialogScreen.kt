package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hoker.supra.presentation.dialogs.SupraMultiSelectionDialog
import com.hoker.supra.presentation.sizes.Sizes

@Composable
fun DialogScreen() {

    var showMultiSelectionDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Sizes.medium),
        verticalArrangement = Arrangement.spacedBy(Sizes.medium)
    ) {
        Button(
            onClick = {
                showMultiSelectionDialog = true
            }
        ) {
            Text(
                text = "Show Multi-selection Dialog"
            )
        }

        SupraMultiSelectionDialog(
            showDialog = showMultiSelectionDialog,
            onDismiss = {
                showMultiSelectionDialog = false
            },
            description = "Example description",
            firstOptionText = "First Option",
            onFirstOptionClicked = {},
            secondOptionText = "Second Option",
            onSecondOptionClicked = {},
            thirdOptionText = "Third Option",
            onThirdOptionClicked = {}
        )
    }
}