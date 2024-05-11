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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.carbidecowboy.supra.presentation.buttons.SupraHardwareButton
import com.carbidecowboy.supra.presentation.cards.SupraTextureCard
import com.carbidecowboy.supra.presentation.cards.TextureType
import com.carbidecowboy.supra.presentation.list_items.SupraTextureListItem
import com.carbidecowboy.supra.presentation.scaffolds.SupraGyroScaffold
import com.carbidecowboy.supra.presentation.scaffolds.SupraScaffold
import com.carbidecowboy.supraexample.domain.models.NavRoutes
import com.carbidecowboy.supraexample.presentation.theme.SupraExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SupraExampleTheme {
                SupraGyroScaffold() {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = NavRoutes.HomeScreen.route
                    ) {
                        composable(NavRoutes.HomeScreen.route) {
                            HomeScreen(
                                onCardsClicked = {
                                    navController.navigate(NavRoutes.CardScreen.route)
                                },
                                onButtonsClicked = {
                                    navController.navigate((NavRoutes.ButtonScreen.route))
                                },
                                onSurfacesClicked = {
                                    navController.navigate((NavRoutes.SurfaceScreen.route))
                                },
                            )
                        }
                        composable(NavRoutes.CardScreen.route) {
                            CardScreen()
                        }
                        composable(NavRoutes.ButtonScreen.route) {
                            ButtonScreen()
                        }
                        composable(NavRoutes.SurfaceScreen.route) {
                            SurfaceScreen()
                        }
                    }
                }
            }
        }
    }
}