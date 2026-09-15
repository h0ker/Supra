package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Key
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.list_items.ColoredButtonListItemShape
import com.hoker.supra.presentation.list_items.SupraColoredButtonListItem
import com.hoker.supra.presentation.list_items.SupraTextureListItem
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel

private data class SlotRow(val title: String, val status: String?)

private val slotRows = listOf(
    SlotRow("Spark 2 · Slot 1", "HMAC-256"),
    SlotRow("Spark 2 · Slot 2", "—"),
    SlotRow("Apex Flex · NDEF", "412 B"),
    SlotRow("NExT · NTAG216", null)
)

@Composable
fun ListItemScreen() {
    // One live row per list: tapping a row moves the live ring to it
    var liveIndex by remember { mutableIntStateOf(0) }

    ScreenColumn {
        SpecimenLabel(text = "Texture rows · flat · tap to move the live ring")
        Column(
            verticalArrangement = Arrangement.spacedBy(Sizes.small)
        ) {
            slotRows.forEachIndexed { index, row ->
                SupraTextureListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Sizes.defaultListItemHeight),
                    title = row.title,
                    leftIconImageVector = Icons.Default.Key,
                    rightIconImageVector = Icons.Default.ChevronRight,
                    status = row.status,
                    live = index == liveIndex,
                    onClick = { liveIndex = index }
                )
            }
        }

        SpecimenLabel(text = "Colored button row · block")
        SupraColoredButtonListItem(
            text = "Enroll",
            width = 220.dp,
            onClick = {}
        )

        SpecimenLabel(text = "Colored button row · capsule")
        SupraColoredButtonListItem(
            text = "Auth",
            shape = ColoredButtonListItemShape.CAPSULE,
            height = 64.dp,
            width = 220.dp,
            onClick = {}
        )
    }
}
