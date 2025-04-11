package com.hoker.supraexample.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.cards.TextureType
import com.hoker.supra.presentation.list_items.SupraTextureListItem
import com.hoker.supraexample.domain.models.NavRoute

@Composable
fun DrawerContent(
    closeDrawer: () -> Unit,
    optionsList: List<NavRoute>,
    onOptionSelected: (NavRoute) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(optionsList) { optionRoute ->
            SupraTextureListItem(
                modifier = Modifier
                    .height(60.dp),
                title = optionRoute.pageTitle,
                rightIconImageVector = Icons.Default.ChevronRight,
                textureType = TextureType.TOPOGRAPHIC,
                backgroundColor = Color.Gray
            ) {
                onOptionSelected(optionRoute)
                closeDrawer()
            }
        }
    }
}