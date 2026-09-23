package com.hoker.supra.presentation.qr_scanner

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.hoker.supra.presentation.buttons.SupraButtonTone
import com.hoker.supra.presentation.buttons.SupraHardwareButton
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraBodyTextMedium
import com.hoker.supra.presentation.text.SupraBodyTextSmall
import com.hoker.supra.presentation.text.SupraTitleTextMedium

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrScannerDialog(
    visible: Boolean,
    onHelpButtonClicked: (() -> Unit)? = null,
    onDismissRequest: () -> Unit,
    onQrCodeScanned: (String) -> Unit
) {

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )

    if (visible) {

        LaunchedEffect(Unit) {
            cameraPermissionState.launchPermissionRequest()
        }


        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            when {
                cameraPermissionState.status.shouldShowRationale -> {
                    PermissionRationale(
                        title = "Camera Permission Required",
                        text = "This app requires camera permission to function",
                        onRequestPermission = {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    )
                }

                cameraPermissionState.status.isGranted -> {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(Sizes.medium)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Sizes.medium)
                        ) {
                            QrScanner(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(Sizes.medium)),
                                codeFoundAction = { contents ->
                                    onQrCodeScanned(contents)
                                    onDismissRequest()
                                }
                            )
                            onHelpButtonClicked?.let { onHelpClicked ->
                                SupraHardwareButton(
                                    text = "Help",
                                    tone = SupraButtonTone.QUIET,
                                    fullWidth = true,
                                    onClick = onHelpClicked
                                )
                            }
                            SupraHardwareButton(
                                text = "Close",
                                tone = SupraButtonTone.NEUTRAL,
                                fullWidth = true,
                                onClick = onDismissRequest
                            )
                        }
                    }
                }

                else -> {
                    SupraBodyTextMedium(
                        text = "Camera permissions were denied. To use the QR scanner, please enable it in the app settings."
                    )
                }
            }
        }
    }
}

@Composable
fun PermissionRationale(
    title: String,
    text: String,
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Sizes.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Sizes.medium)
    ) {
        SupraTitleTextMedium(
            text = title,
            textAlignment = TextAlign.Center
        )
        SupraBodyTextSmall(
            text = text,
            textAlignment = TextAlign.Center
        )
        SupraHardwareButton(
            text = "Grant permission",
            fullWidth = true,
            onClick = onRequestPermission
        )
    }
}