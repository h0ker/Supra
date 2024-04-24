package com.carbidecowboy.supraexample.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.carbidecowboy.supra.presentation.buttons.SupraHardwareButton
import com.carbidecowboy.supra.presentation.cards.SupraTextureCard
import com.carbidecowboy.supra.presentation.cards.TextureType
import com.carbidecowboy.supra.presentation.list_items.SupraTextureListItem
import com.carbidecowboy.supra.presentation.scaffolds.SupraGyroScaffold
import com.carbidecowboy.supra.presentation.scaffolds.SupraScaffold
import com.carbidecowboy.supraexample.presentation.theme.SupraExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SupraExampleTheme {
                SupraScaffold( borderColor = Color.LightGray, backgroundColor = Color.DarkGray ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        //Example Texture Card
                        item {
                            SupraTextureCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp),
                                textureType = TextureType.TOPOGRAPHIC,
                                backgroundColor = Color.Yellow,
                            ) {

                            }
                        }

                        //Example Texture List Item
                        item {
                            SupraTextureListItem(
                                modifier = Modifier
                                    .height(60.dp),
                                title = "Big Poopies",
                                leftIconImageVector = Icons.Default.Person,
                                rightIconImageVector = Icons.Default.ChevronRight,
                                textureType = TextureType.TOPOGRAPHIC,
                                backgroundColor = Color.Gray
                            ) {
                                Toast.makeText(this@MainActivity, "Big Poopies", Toast.LENGTH_SHORT).show()
                            }
                        }

                        //Example Hardware Buttons
                        item{
                            Row (
                                modifier = Modifier.fillMaxSize(),
                                Arrangement.SpaceBetween
                            ) {
                                SupraHardwareButton(
                                    modifier = Modifier
                                        .size(100.dp),
                                    buttonText = "GB",
                                    backgroundColor = Color(0xFFE9D30E),
                                ) {}
                                SupraHardwareButton(
                                    modifier = Modifier
                                        .size(64.dp),
                                    outerCornerRadius = 4.dp,
                                    innerCornerRadius = 4.dp ,
                                    buttonText = "Text",
                                    backgroundColor = Color(0xFF04B4BA),
                                ) {}
                                SupraHardwareButton(
                                    modifier = Modifier
                                        .height(70.dp)
                                        .width(150.dp),
                                    outerCornerRadius = 16.dp,
                                    innerCornerRadius = 8.dp ,
                                    buttonText = "Text",
                                    backgroundColor = Color(0xFFD8D4CF),
                                ) {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }