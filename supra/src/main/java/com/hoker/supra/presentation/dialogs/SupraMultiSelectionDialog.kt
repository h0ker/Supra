package com.hoker.supra.presentation.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hoker.supra.presentation.shapes.CustomCornersShape

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
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        text = description,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    Card(
                        modifier = Modifier
                            .clickable {
                                onFirstOptionClicked()
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .clip(CustomCornersShape(16.dp, 16.dp, 0.dp, 0.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary,
                            text = firstOptionText
                        )
                    }

                    if (secondOptionText != null) {
                        Card(
                            modifier = Modifier
                                .clickable {
                                    onSecondOptionClicked?.invoke()
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.primary,
                                text = secondOptionText
                            )
                        }
                    }

                    if (thirdOptionText != null) {
                        Card(
                            modifier = Modifier
                                .clickable {
                                    onThirdOptionClicked?.invoke()
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .clip(CustomCornersShape(0.dp, 0.dp, 16.dp, 16.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.primary,
                                text = thirdOptionText
                            )
                        }
                    }
                }
            }
        }
    }
}