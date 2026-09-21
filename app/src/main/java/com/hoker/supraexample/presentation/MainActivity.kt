package com.hoker.supraexample.presentation

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hoker.supra.di.SupraModule
import com.hoker.supra.presentation.entries.SupraTextField
import com.hoker.supra.presentation.fx.glitchEffect
import com.hoker.supra.presentation.scaffolds.SupraBackground
import com.hoker.supra.presentation.scaffolds.SupraScaffold
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.SupraTheme
import com.hoker.supra.presentation.theme.UiTheme
import com.hoker.supraexample.domain.models.ChromeSettings
import com.hoker.supraexample.domain.models.Consts
import com.hoker.supraexample.domain.models.NavRoute
import com.hoker.supraexample.presentation.screens.ButtonScreen
import com.hoker.supraexample.presentation.screens.ChromeScreen
import com.hoker.supraexample.presentation.screens.ColorScreen
import com.hoker.supraexample.presentation.screens.DialogScreen
import com.hoker.supraexample.presentation.screens.EntriesScreen
import com.hoker.supraexample.presentation.screens.FullscreenEntryScreen
import com.hoker.supraexample.presentation.screens.HomeScreen
import com.hoker.supraexample.presentation.screens.IndicatorScreen
import com.hoker.supraexample.presentation.screens.ListItemScreen
import com.hoker.supraexample.presentation.screens.MaterialScreen
import com.hoker.supraexample.presentation.screens.PickersScreen
import com.hoker.supraexample.presentation.screens.ScanScreen
import com.hoker.supraexample.presentation.screens.QrScannerScreen
import com.hoker.supraexample.presentation.screens.SnackbarScreen
import com.hoker.supraexample.presentation.screens.SupraFXScreen
import com.hoker.supraexample.presentation.screens.TextScreen
import com.hoker.supraexample.presentation.screens.TextureScreen
import com.hoker.supraexample.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SupraModule.SupraSharedPrefs
    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()

            var currentRoute by remember { mutableStateOf<NavRoute>(NavRoute.HomeScreen) }
            val navController = rememberNavController()

            var glitchKey by remember { mutableIntStateOf(0) }
            var chromeSettings by remember { mutableStateOf(ChromeSettings()) }

            val mainViewModel: MainViewModel = hiltViewModel()

            val isDarkModeEnabled = mainViewModel.isDarkModeEnabled.collectAsState()
            var uiThemeSelection by remember { mutableStateOf(UiTheme.fromTitle(sharedPreferences.getString(Consts.SETTINGS_SELECTED_UI_THEME, UiTheme.LIVE.title))) }

            sharedPreferences.registerOnSharedPreferenceChangeListener { pref, key ->
                if (key == Consts.SETTINGS_SELECTED_UI_THEME) {
                    uiThemeSelection = UiTheme.fromTitle(pref.getString(key, UiTheme.LIVE.title))
                    glitchKey = Random.nextInt()
                }
            }

            // Home sits outside the drawer menu, so it reads as index 00
            val routeIndex = NavRoute.menuOptions.indexOf(currentRoute) + 1
            val versionName = remember { packageManager.getPackageInfo(packageName, 0).versionName }

            SupraTheme(
                customColorScheme = uiThemeSelection.colorScheme
            ) {
                SupraScaffold(
                    modifier = Modifier.glitchEffect(
                        key = glitchKey,
                        glitchColors = remember { listOf(Color.Cyan, Color.Yellow, Color.Magenta) },
                        slices = 40
                    ),
                    brackets = chromeSettings.brackets,
                    title = currentRoute.pageTitle.takeIf { chromeSettings.cornerTitle },
                    readout = if (chromeSettings.cornerTitle) {
                        null to "%02d / %02d".format(routeIndex, NavRoute.menuOptions.size)
                    } else {
                        null
                    },
                    rail = chromeSettings.rail,
                    railStamp = "Rev $versionName".takeIf { chromeSettings.railStamp },
                    railMeter = chromeSettings.railMeter,
                    // The bracket names the screen, so the bar carries only actions
                    topBar = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
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
                    customTopBar = if (chromeSettings.customTopBar) {
                        { FilterTopBar(handleStatusBar = !chromeSettings.cornerTitle) }
                    } else {
                        null
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
                            currentRoute = NavRoute.HomeScreen
                            HomeScreen()
                        }
                        composable(NavRoute.MaterialScreen.route) {
                            currentRoute = NavRoute.MaterialScreen
                            MaterialScreen()
                        }
                        composable(NavRoute.TextureScreen.route) {
                            currentRoute = NavRoute.TextureScreen
                            TextureScreen {
                                navController.popBackStack()
                            }
                        }
                        composable(NavRoute.ButtonScreen.route) {
                            currentRoute = NavRoute.ButtonScreen
                            ButtonScreen {
                                navController.popBackStack()
                            }
                        }
                        composable(NavRoute.ListItemScreen.route) {
                            currentRoute = NavRoute.ListItemScreen
                            ListItemScreen()
                        }
                        composable(NavRoute.EntriesScreen.route) {
                            currentRoute = NavRoute.EntriesScreen
                            EntriesScreen {
                                navController.popBackStack()
                            }
                        }
                        composable(NavRoute.FullscreenEntryScreen.route) {
                            currentRoute = NavRoute.FullscreenEntryScreen
                            FullscreenEntryScreen()
                        }
                        composable(NavRoute.PickerScreen.route) {
                            currentRoute = NavRoute.PickerScreen
                            PickersScreen()
                        }
                        composable(NavRoute.DialogScreen.route) {
                            currentRoute = NavRoute.DialogScreen
                            DialogScreen()
                        }
                        composable(NavRoute.SnackbarScreen.route) {
                            currentRoute = NavRoute.SnackbarScreen
                            SnackbarScreen()
                        }
                        composable(NavRoute.IndicatorScreen.route) {
                            currentRoute = NavRoute.IndicatorScreen
                            IndicatorScreen()
                        }
                        composable(NavRoute.ScanScreen.route) {
                            currentRoute = NavRoute.ScanScreen
                            ScanScreen()
                        }
                        composable(NavRoute.SupraFXScreen.route) {
                            currentRoute = NavRoute.SupraFXScreen
                            SupraFXScreen()
                        }
                        composable(NavRoute.ChromeScreen.route) {
                            currentRoute = NavRoute.ChromeScreen
                            ChromeScreen(
                                settings = chromeSettings,
                                onSettingsChanged = { chromeSettings = it }
                            )
                        }
                        composable(NavRoute.ColorScreen.route) {
                            currentRoute = NavRoute.ColorScreen
                            ColorScreen(
                                themeInitialState = uiThemeSelection.title,
                                onThemeStateChanged = { theme ->
                                    mainViewModel.updateSettingState(Consts.SETTINGS_SELECTED_UI_THEME, theme)
                                }
                            )
                        }
                        composable(NavRoute.TextScreen.route) {
                            currentRoute = NavRoute.TextScreen
                            TextScreen()
                        }
                        composable(NavRoute.QrScannerScreen.route) {
                            currentRoute = NavRoute.QrScannerScreen
                            QrScannerScreen()
                        }
                    }
                }
            }
        }
    }
}

/**
 * An app-specific bar, like the credential filter in the authenticator app, swapped in wholesale.
 */
@Composable
private fun FilterTopBar(handleStatusBar: Boolean) {
    var filter by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (handleStatusBar) Modifier.statusBarsPadding() else Modifier)
            .padding(vertical = Sizes.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SupraTextField(
            modifier = Modifier.weight(1f),
            value = filter,
            label = "Filter devices",
            labelBackgroundColor = MaterialTheme.colorScheme.primary,
            singleLine = true,
            onValueChange = { filter = it }
        )
        IconButton(onClick = {}) {
            Icon(Icons.Filled.Lock, contentDescription = "Lock")
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
