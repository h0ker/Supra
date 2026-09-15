package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.cards.MaterialStyle
import com.hoker.supra.presentation.cards.SupraTextureCard
import com.hoker.supra.presentation.list_items.SupraTextureListItem
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraDataGutter
import com.hoker.supra.presentation.text.SupraDisplayBlock
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel
import com.hoker.supraexample.presentation.components.Wordmark

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val versionName = remember { context.packageManager.getPackageInfo(context.packageName, 0).versionName }

    ScreenColumn {
        SupraTextureCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            material = MaterialStyle.HERO
        ) {
            Wordmark(sub = "Design system · Rev $versionName")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(Sizes.medium)
        ) {
            SupraDisplayBlock(
                modifier = Modifier.weight(1f),
                text = "Scan\nyour\nkey"
            )
            SupraDataGutter(
                rows = listOf(
                    "UID" to "04A29F3C",
                    "Slot" to "01",
                    "Alg" to "HMAC-256",
                    "Ctr" to "000412",
                    "RSSI" to "-41 dBm"
                )
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(Sizes.small)
        ) {
            SpecimenLabel(text = "Paired devices · 3")
            SupraTextureListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Sizes.defaultListItemHeight),
                title = "Spark 2 · Slot 1",
                status = "Active",
                live = true,
                rightIconImageVector = Icons.Default.ChevronRight,
                onClick = {}
            )
            SupraTextureListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Sizes.defaultListItemHeight),
                title = "Apex Flex · Slot 2",
                status = "—",
                rightIconImageVector = Icons.Default.ChevronRight,
                onClick = {}
            )
            SupraTextureListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Sizes.defaultListItemHeight),
                title = "NExT · NTAG216",
                status = "—",
                rightIconImageVector = Icons.Default.ChevronRight,
                onClick = {}
            )
        }
    }
}
