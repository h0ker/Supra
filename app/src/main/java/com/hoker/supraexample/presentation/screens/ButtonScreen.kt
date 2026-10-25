package com.hoker.supraexample.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.buttons.SupraButtonTone
import com.hoker.supra.presentation.buttons.SupraHardwareButton
import com.hoker.supra.presentation.buttons.SupraIconButtonSizeLarge
import com.hoker.supra.presentation.buttons.SupraKeyedButton
import com.hoker.supra.presentation.buttons.SupraOutlinedButton
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ButtonScreen(
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }

    ScreenColumn {
        SpecimenLabel(text = "Accent · full width · the one committing action")
        SupraHardwareButton(
            text = "Write key",
            icon = Icons.Filled.Nfc,
            fullWidth = true,
            onClick = {}
        )

        SpecimenLabel(text = "Neutral · full width")
        SupraHardwareButton(
            text = "Cancel",
            tone = SupraButtonTone.NEUTRAL,
            fullWidth = true,
            onClick = {}
        )

        SpecimenLabel(text = "Quiet · danger · disabled · auto width")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Sizes.small),
            verticalArrangement = Arrangement.spacedBy(Sizes.small)
        ) {
            SupraHardwareButton(
                text = "Retry",
                tone = SupraButtonTone.QUIET,
                onClick = {}
            )
            SupraHardwareButton(
                text = "Erase slot",
                tone = SupraButtonTone.DANGER,
                onClick = {}
            )
            SupraHardwareButton(
                text = "Locked",
                enabled = false,
                onClick = {}
            )
        }

        SpecimenLabel(text = "Icon only · 48 × 48 · same lip and press as a labelled button")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Sizes.small)
        ) {
            SupraHardwareButton(
                icon = Icons.Filled.Refresh,
                contentDescription = "Rescan tag",
                tone = SupraButtonTone.NEUTRAL,
                onClick = {}
            )
            SupraHardwareButton(
                icon = Icons.Filled.ContentCopy,
                contentDescription = "Copy key",
                tone = SupraButtonTone.QUIET,
                onClick = {}
            )
            SupraHardwareButton(
                icon = Icons.Filled.Share,
                contentDescription = "Share key",
                tone = SupraButtonTone.QUIET,
                onClick = {}
            )
            SupraHardwareButton(
                icon = Icons.Filled.Lock,
                contentDescription = "Lock",
                enabled = false,
                onClick = {}
            )
            SupraHardwareButton(
                text = "Details",
                tone = SupraButtonTone.NEUTRAL,
                onClick = {}
            )
        }

        SpecimenLabel(text = "Icon only · large · 56 × 56 · glyph scales with the square")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Sizes.small)
        ) {
            SupraHardwareButton(
                icon = Icons.Filled.QrCodeScanner,
                contentDescription = "Scan QR code",
                tone = SupraButtonTone.NEUTRAL,
                size = SupraIconButtonSizeLarge,
                onClick = {}
            )
            SupraHardwareButton(
                icon = Icons.Filled.Settings,
                contentDescription = "Settings",
                tone = SupraButtonTone.QUIET,
                size = SupraIconButtonSizeLarge,
                onClick = {}
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Sizes.medium)
        ) {
            SupraHardwareButton(
                text = "Scan",
                tone = SupraButtonTone.NEUTRAL,
                squareSize = 88.dp,
                onClick = {}
            )
            SpecimenLabel(
                modifier = Modifier.weight(1f),
                text = "Square size · original keycap · hardware moments only"
            )
        }

        SpecimenLabel(text = "Keyed · flat · top-start cut · press is a tone shift only")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Sizes.small),
            verticalArrangement = Arrangement.spacedBy(Sizes.small)
        ) {
            SupraKeyedButton(
                text = "Export",
                tone = SupraButtonTone.NEUTRAL,
                onClick = {}
            )
            SupraKeyedButton(
                text = "Rename",
                tone = SupraButtonTone.QUIET,
                onClick = {}
            )
            SupraKeyedButton(
                text = "Pair",
                outlined = true,
                onClick = {}
            )
            SupraKeyedButton(
                text = "Wipe",
                tone = SupraButtonTone.DANGER,
                onClick = {}
            )
            SupraKeyedButton(
                text = "Locked",
                enabled = false,
                onClick = {}
            )
        }
        SupraKeyedButton(
            text = "Duplicate slot",
            icon = Icons.Filled.ContentCopy,
            tone = SupraButtonTone.NEUTRAL,
            outlined = true,
            fullWidth = true,
            onClick = {}
        )

        SpecimenLabel(text = "Outlined · the quiet counterpart")
        SupraOutlinedButton(
            text = "View key details",
            onClick = {}
        )
    }
}
