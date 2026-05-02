package com.hoker.supra.presentation.scaffolds

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hoker.supra.presentation.cards.TextureType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupraScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    drawerContent: (@Composable (onCloseDrawer: () -> Unit) -> Unit)? = null,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentBackgroundColor: Color = MaterialTheme.colorScheme.background,
    textureType: TextureType? = null,
    content: @Composable (modifier: Modifier) -> Unit
){
    if (drawerContent != null) {

        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val closeDrawer: () -> Unit = {
            scope.launch {
                drawerState.close()
            }
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = borderColor
                ) {
                    drawerContent(closeDrawer)
                }
            }
        ) {
            SupraFXScaffold(
                modifier = modifier,
                topBar = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent
                            ),
                            title = {
                                topBar?.invoke()
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }
                        )
                    }
                },
                bottomBar = bottomBar,
                borderColor = borderColor,
                contentBackgroundColor = contentBackgroundColor,
                textureType = textureType,
                content = content
            )
        }
    } else {
        SupraFXScaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            borderColor = borderColor,
            contentBackgroundColor = contentBackgroundColor,
            textureType = textureType,
            content = content
        )
    }
}