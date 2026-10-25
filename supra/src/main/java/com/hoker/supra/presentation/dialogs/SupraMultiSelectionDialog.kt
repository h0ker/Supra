package com.hoker.supra.presentation.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraBodyTextMedium
import com.hoker.supra.presentation.text.SupraTitleTextSmall
import com.hoker.supra.presentation.theme.Ink3

@Composable
fun SupraMultiSelectionDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    description: String,
    firstOptionText: String,
    secondOptionText: String? = null,
    thirdOptionText: String? = null,
    onFirstOptionClicked: () -> Unit,
    onSecondOptionClicked: (() -> Unit)? = null,
    onThirdOptionClicked: (() -> Unit)? = null
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                onDismiss()
            }
        ) {
            Card(
                shape = SupraShapes.control,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    SupraTitleTextSmall(
                        modifier = Modifier
                            .padding(bottom = Sizes.small)
                            .fillMaxWidth(),
                        text = description,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlignment = TextAlign.Center
                    )
                    SelectionBlock(
                        text = firstOptionText,
                        onClick = onFirstOptionClicked
                    )
                    if (secondOptionText != null) {
                        SelectionBlock(
                            text = secondOptionText,
                            onClick = { onSecondOptionClicked?.invoke() }
                        )
                    }
                    if (thirdOptionText != null) {
                        SelectionBlock(
                            text = thirdOptionText,
                            onClick = { onThirdOptionClicked?.invoke() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectionBlock(
    text: String,
    onClick: () -> Unit
) {
    SupraBodyTextMedium(
        modifier = Modifier
            .padding(horizontal = Sizes.small)
            .fillMaxWidth()
            .clip(SupraShapes.control)
            .background(Ink3)
            .clickable { onClick() }
            .padding(12.dp),
        textAlignment = TextAlign.Center,
        color = MaterialTheme.colorScheme.onPrimary,
        text = text
    )
}
