package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hoker.supra.presentation.qr_scanner.QrScannerDialog
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraBodyTextMedium
import com.hoker.supra.presentation.text.SupraTitleTextSmall

@Composable
fun QrScannerScreen() {

    var showQrScanner by remember { mutableStateOf(false) }
    var qrScanContent by remember { mutableStateOf("") }

    QrScannerScreenContent(
        showQrScanner = showQrScanner,
        qrScanContent = qrScanContent,
        onQrScannerVisibilityChanged = { visible ->
            showQrScanner = visible
        },
        onQrCodeDiscovered = { content ->
            qrScanContent = content
        }
    )
}

@Composable
fun QrScannerScreenContent(
    showQrScanner: Boolean,
    qrScanContent: String?,
    onQrScannerVisibilityChanged: (Boolean) -> Unit,
    onQrCodeDiscovered: (contents: String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(Sizes.medium)
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    onQrScannerVisibilityChanged(true)
                }
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    SupraTitleTextSmall(text = "Show Qr Scanner")
                }
            }
            SupraTitleTextSmall(text = "Qr Scan Content:")
            SupraBodyTextMedium(text = qrScanContent ?: "")
        }
        QrScannerDialog(
            visible = showQrScanner,
            onDismissRequest = {
                onQrScannerVisibilityChanged(false)
            },
            onQrCodeScanned = onQrCodeDiscovered
        )
    }
}

@Preview
@Composable
fun QrScannerScreenPreview() {
    QrScannerScreenContent(
        showQrScanner = false,
        qrScanContent = "Example",
        onQrScannerVisibilityChanged = {},
        onQrCodeDiscovered = {}
    )
}