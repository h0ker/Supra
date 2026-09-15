package com.hoker.supraexample.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.buttons.SupraButtonTone
import com.hoker.supra.presentation.buttons.SupraHardwareButton
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

        SpecimenLabel(text = "Outlined · the quiet counterpart")
        SupraOutlinedButton(
            text = "View key details",
            onClick = {}
        )
    }
}
