package com.hoker.supra.presentation.scaffolds

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * @param topBar Content for the built-in top bar row (next to the drawer button when [drawerContent] is set).
 * @param customTopBar An app-specific bar that replaces the built-in row wholesale.
 * @param title Bracket furniture: the current screen name, tucked into the top-left bracket. Because the
 *              bracket names the screen, the top bar usually needs no title of its own.
 * @param readout Left value is used when [title] is null; right value is the index shown in the top-right
 *                bracket. Both must be genuine data.
 * @param railMeter Drive the rail from the magnetometer. Leave off for screens with nothing to measure.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupraScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    customTopBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    drawerContent: (@Composable (onCloseDrawer: () -> Unit) -> Unit)? = null,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentBackgroundColor: Color = MaterialTheme.colorScheme.background,
    background: SupraBackground? = null,
    brackets: Boolean = true,
    title: String? = null,
    readout: Pair<String?, String?>? = null,
    rail: Boolean = false,
    railStamp: String? = null,
    railMeter: Boolean = false,
    content: @Composable (modifier: Modifier) -> Unit
){
    // With bracket furniture, SupraFXScaffold already consumes the status bar inset
    val hasCorner = title != null || readout?.first != null || readout?.second != null

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
                topBar = customTopBar ?: {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TopAppBar(
                            windowInsets = if (hasCorner) WindowInsets(0) else TopAppBarDefaults.windowInsets,
                            // Keep the bar tight under the bracket furniture
                            expandedHeight = if (hasCorner) 48.dp else TopAppBarDefaults.TopAppBarExpandedHeight,
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
                background = background,
                brackets = brackets,
                title = title,
                readout = readout,
                rail = rail,
                railStamp = railStamp,
                railMeter = railMeter,
                content = content
            )
        }
    } else {
        SupraFXScaffold(
            modifier = modifier,
            topBar = customTopBar ?: topBar,
            bottomBar = bottomBar,
            borderColor = borderColor,
            contentBackgroundColor = contentBackgroundColor,
            background = background,
            brackets = brackets,
            title = title,
            readout = readout,
            rail = rail,
            railStamp = railStamp,
            railMeter = railMeter,
            content = content
        )
    }
}
