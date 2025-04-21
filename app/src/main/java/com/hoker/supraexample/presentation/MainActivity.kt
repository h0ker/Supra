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
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hoker.supra.presentation.scaffolds.SupraScaffold
import com.hoker.supra.presentation.sizes.FontSizes
import com.hoker.supra.presentation.theme.SupraTheme
import com.hoker.supraexample.domain.models.NavRoute
import com.hoker.supraexample.presentation.screens.ButtonScreen
import com.hoker.supraexample.presentation.screens.CardScreen
import com.hoker.supraexample.presentation.screens.ColorScreen
import com.hoker.supraexample.presentation.screens.DialogScreen
import com.hoker.supraexample.presentation.screens.EntriesScreen
import com.hoker.supraexample.presentation.screens.HomeScreen
import com.hoker.supraexample.presentation.screens.SupraFXScreen
import com.hoker.supraexample.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            val titleFont = FontFamily(Font(com.hoker.supra.R.font.univers_light))
            var titleText by remember { mutableStateOf(NavRoute.HomeScreen.pageTitle) }
            val navController = rememberNavController()

            val mainViewModel: MainViewModel = hiltViewModel()

            val isDarkModeEnabled = mainViewModel.isDarkModeEnabled.collectAsState()
            val isLoading = mainViewModel.supraFX.isLoading.collectAsState()

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
                            Text(
                                text = titleText,
                                fontFamily = titleFont,
                                fontSize = FontSizes.large
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
                    oneshotChannel = mainViewModel.supraFX.oneshotChannel,
                    isLoading = isLoading.value
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavRoute.HomeScreen.route
                    ) {
                        composable(NavRoute.HomeScreen.route) {
                            titleText = NavRoute.HomeScreen.pageTitle
                            HomeScreen()
                        }
                        composable(NavRoute.CardScreen.route) {
                            titleText = NavRoute.CardScreen.pageTitle
                            CardScreen {
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
                            SupraFXScreen(
                                onToggleLoadingClicked = {
                                    mainViewModel.supraFX.setIsLoading(!isLoading.value)
                                },
                                onFirePulse = { color ->
                                    mainViewModel.supraFX.firePulse(color)
                                }
                            )
                        }
                        composable(NavRoute.ColorScreen.route) {
                            titleText = NavRoute.ColorScreen.pageTitle
                            ColorScreen()
                        }
                        composable(NavRoute.DialogScreen.route) {
                            titleText = NavRoute.DialogScreen.pageTitle
                            DialogScreen()
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