package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hoker.supra.presentation.buttons.SupraHardwareButton
import com.hoker.supra.presentation.qr_scanner.QrScannerDialog
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.theme.DataBlue
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel

@Composable
fun QrScannerScreen() {

    var showQrScanner by remember { mutableStateOf(false) }
    var qrScanContent by remember { mutableStateOf<String?>(null) }

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
        ScreenColumn(spacing = Sizes.small) {
            SupraHardwareButton(
                text = "Scan QR code",
                icon = Icons.Filled.QrCodeScanner,
                fullWidth = true,
                onClick = {
                    onQrScannerVisibilityChanged(true)
                }
            )
            SpecimenLabel(text = "QR scan content")
            Text(
                text = qrScanContent?.takeIf { it.isNotEmpty() } ?: "—",
                style = MaterialTheme.typography.labelMedium,
                color = DataBlue
            )
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
        qrScanContent = "vivokey://spark2/04A29F3C1180",
        onQrScannerVisibilityChanged = {},
        onQrCodeDiscovered = {}
    )
}
