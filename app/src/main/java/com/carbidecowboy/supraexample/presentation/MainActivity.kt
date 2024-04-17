package com.carbidecowboy.supraexample.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carbidecowboy.supra.presentation.cards.SupraTextureCard
import com.carbidecowboy.supra.presentation.cards.TextureType
import com.carbidecowboy.supra.presentation.list_items.SupraTextureListItem
import com.carbidecowboy.supra.presentation.scaffolds.SupraGyroScaffold
import com.carbidecowboy.supraexample.presentation.theme.SupraExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SupraExampleTheme {
                SupraGyroScaffold {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        //Example Texture Card
                        item {
                            SupraTextureCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                                    .padding(16.dp),
                                textureType = TextureType.TOPOGRAPHIC,
                                backgroundColor = Color.Yellow,
                                tint = Color(android.graphics.Color.parseColor("#FFCE00"))
                            ) {

                            }
                        }

                        //Example Texture List Item
                        item {
                            SupraTextureListItem(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .height(60.dp),
                                title = "Big Poopies",
                                leftIconImageVector = Icons.Default.Person,
                                rightIconImageVector = Icons.Default.ChevronRight,
                                textureType = TextureType.TOPOGRAPHIC,
                                backgroundColor = Color.Yellow,
                                tint = Color(android.graphics.Color.parseColor("#FFCE00"))
                            ) {
                                Toast.makeText(this@MainActivity, "Big Poopies", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}