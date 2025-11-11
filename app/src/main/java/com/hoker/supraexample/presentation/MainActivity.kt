package com.hoker.supraexample.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hoker.supra.presentation.scaffolds.SupraScaffold
import com.hoker.supra.presentation.text.SupraTitleTextMedium
import com.hoker.supra.presentation.theme.SupraTheme
import com.hoker.supraexample.domain.models.NavRoute
import com.hoker.supraexample.presentation.screens.ButtonScreen
import com.hoker.supraexample.presentation.screens.TextureScreen
import com.hoker.supraexample.presentation.screens.ColorScreen
import com.hoker.supraexample.presentation.screens.DialogScreen
import com.hoker.supraexample.presentation.screens.EntriesScreen
import com.hoker.supraexample.presentation.screens.HomeScreen
import com.hoker.supraexample.presentation.screens.PickersScreen
import com.hoker.supraexample.presentation.screens.QrScannerScreen
import com.hoker.supraexample.presentation.screens.SupraFXScreen
import com.hoker.supraexample.presentation.screens.TextScreen
import com.hoker.supraexample.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            var titleText by remember { mutableStateOf(NavRoute.HomeScreen.pageTitle) }
            val navController = rememberNavController()

            val mainViewModel: MainViewModel = hiltViewModel()

            val isDarkModeEnabled = mainViewModel.isDarkModeEnabled.collectAsState()

            SupraTheme(
                darkTheme = isDarkModeEnabled.value
            ) {
                SupraScaffold(
                    topBar = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SupraTitleTextMedium(
                                text = titleText
                            )
                            IconButton(
                                onClick = {
                                    mainViewModel.toggleDarkMode()
                                }
                            ) {
                                Icon(
                                    if (isDarkModeEnabled.value) Icons.Filled.ModeNight else Icons.Filled.WbSunny,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    drawerContent = { closeDrawer ->
                        DrawerContent(
                            closeDrawer = closeDrawer,
                            optionsList = NavRoute.menuOptions,
                            onOptionSelected = { optionRoute ->
                                navController.replaceCurrentRoute(optionRoute.route)
                            }
                        )
                    },
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavRoute.HomeScreen.route
                    ) {
                        composable(NavRoute.HomeScreen.route) {
                            titleText = NavRoute.HomeScreen.pageTitle
                            HomeScreen()
                        }
                        composable(NavRoute.TextureScreen.route) {
                            titleText = NavRoute.TextureScreen.pageTitle
                            TextureScreen {
                                navController.popBackStack()
                            }
                        }
                        composable(NavRoute.ButtonScreen.route) {
                            titleText = NavRoute.ButtonScreen.pageTitle
                            ButtonScreen {
                                navController.popBackStack()
                            }
                        }
                        composable(NavRoute.EntriesScreen.route) {
                            titleText = NavRoute.EntriesScreen.pageTitle
                            EntriesScreen {
                                navController.popBackStack()
                            }
                        }
                        composable(NavRoute.SupraFXScreen.route) {
                            titleText = NavRoute.SupraFXScreen.pageTitle
                            SupraFXScreen()
                        }
                        composable(NavRoute.ColorScreen.route) {
                            titleText = NavRoute.ColorScreen.pageTitle
                            ColorScreen()
                        }
                        composable(NavRoute.DialogScreen.route) {
                            titleText = NavRoute.DialogScreen.pageTitle
                            DialogScreen()
                        }
                        composable(NavRoute.TextScreen.route) {
                            titleText = NavRoute.TextScreen.pageTitle
                            TextScreen()
                        }
                        composable(NavRoute.PickerScreen.route) {
                            titleText = NavRoute.PickerScreen.pageTitle
                            PickersScreen()
                        }
                        composable(NavRoute.QrScannerScreen.route) {
                            titleText = NavRoute.QrScannerScreen.pageTitle
                            QrScannerScreen()
                        }
                    }
                }
            }
        }
    }
}

fun NavController.replaceCurrentRoute(route: String) {
    this.navigate(route) {
        if (this@replaceCurrentRoute.currentDestination?.route != NavRoute.HomeScreen.route) {
            popUpTo(this@replaceCurrentRoute.currentDestination?.route ?: "") {
                inclusive = true
            }
        }
        launchSingleTop = true
    }
}