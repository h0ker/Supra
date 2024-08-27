package com.hoker.supraexample.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hoker.supra.presentation.scaffolds.SupraGyroScaffold
import com.hoker.supraexample.domain.models.NavRoutes
import com.hoker.supraexample.presentation.theme.SupraExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val titleFont = FontFamily(Font(com.hoker.supra.R.font.univers_light))

            SupraExampleTheme {
                SupraGyroScaffold(
                    borderColor = Color.LightGray,
                    backgroundColor = Color.DarkGray,
                    topBar = {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "SUPRA",
                                fontFamily = titleFont,
                                fontSize = 32.sp,
                                color = Color.Black
                            )
                        }
                    }
                ) {

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
                                }
                            )
                        }
                        composable(NavRoutes.CardScreen.route) {
                            CardScreen()
                        }
                        composable(NavRoutes.ButtonScreen.route) {
                            ButtonScreen()
                        }
                    }
                }
            }
        }
    }
}