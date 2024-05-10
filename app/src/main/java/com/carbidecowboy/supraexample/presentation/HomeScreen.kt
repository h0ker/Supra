package com.carbidecowboy.supraexample.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.carbidecowboy.supra.presentation.buttons.SupraHardwareButton
import com.carbidecowboy.supra.presentation.cards.SupraTextureCard
import com.carbidecowboy.supra.presentation.cards.TextureType
import com.carbidecowboy.supra.presentation.list_items.SupraTextureListItem

@Composable
fun HomeScreen(
    onCardsClicked: () -> Unit,
    onButtonsClicked: () -> Unit,
    onEntriesClicked: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        //Example Texture List Item
        item {
            SupraTextureListItem(
                modifier = Modifier
                    .height(60.dp),
                title = "Cards",
                rightIconImageVector = Icons.Default.ChevronRight,
                textureType = TextureType.TOPOGRAPHIC,
                backgroundColor = Color.Gray
            ) {
                onCardsClicked()
            }
        }
        item {
            SupraTextureListItem(
                modifier = Modifier
                    .height(60.dp),
                title = "Buttons",
                rightIconImageVector = Icons.Default.ChevronRight,
                textureType = TextureType.TOPOGRAPHIC,
                backgroundColor = Color.Gray
            ) {
                onButtonsClicked()
            }
        }
        item {
            SupraTextureListItem(
                modifier = Modifier
                    .height(60.dp),
                title = "Entries",
                rightIconImageVector = Icons.Default.ChevronRight,
                textureType = TextureType.TOPOGRAPHIC,
                backgroundColor = Color.Gray
            ) {
                onEntriesClicked()
            }
        }
    }
}