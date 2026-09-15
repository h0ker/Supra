package com.hoker.supraexample.presentation.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hoker.supra.presentation.buttons.SupraButtonTone
import com.hoker.supra.presentation.buttons.SupraHardwareButton
import com.hoker.supra.presentation.dialogs.SupraMultiSelectionDialog
import com.hoker.supra.presentation.theme.DataBlue
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel

@Composable
fun DialogScreen() {

    var showMultiSelectionDialog by remember { mutableStateOf(false) }
    var selection by remember { mutableStateOf<String?>(null) }

    ScreenColumn {
        SupraHardwareButton(
            text = "Choose slot",
            tone = SupraButtonTone.NEUTRAL,
            onClick = {
                showMultiSelectionDialog = true
            }
        )

        SpecimenLabel(text = "Selected slot")
        Text(
            text = selection ?: "—",
            style = MaterialTheme.typography.labelMedium,
            color = DataBlue
        )

        SupraMultiSelectionDialog(
            showDialog = showMultiSelectionDialog,
            onDismiss = {
                showMultiSelectionDialog = false
            },
            description = "Write key to which slot?",
            firstOptionText = "Slot 1",
            onFirstOptionClicked = {
                selection = "Slot 1"
                showMultiSelectionDialog = false
            },
            secondOptionText = "Slot 2",
            onSecondOptionClicked = {
                selection = "Slot 2"
                showMultiSelectionDialog = false
            },
            thirdOptionText = "Cancel",
            onThirdOptionClicked = {
                showMultiSelectionDialog = false
            }
        )
    }
}
