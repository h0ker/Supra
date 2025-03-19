package com.hoker.supraexample.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.hoker.supraexample.domain.models.NavRoute
import com.hoker.supraexample.presentation.theme.SupraExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val titleFont = FontFamily(Font(com.hoker.supra.R.font.univers_light))
            var titleText by remember { mutableStateOf(NavRoute.HomeScreen.pageTitle) }

            SupraExampleTheme {
                SupraGyroScaffold(
                    borderColor = Color.LightGray,
                    backgroundColor = Color.DarkGray,
                    topBar = {
                        Row(
                            modifier = Modifier
                                .statusBarsPadding()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = titleText,
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
                        startDestination = NavRoute.HomeScreen.route
                    ) {
                        composable(NavRoute.HomeScreen.route) {
                            titleText = NavRoute.HomeScreen.pageTitle
                            HomeScreen(
                                optionList = NavRoute.menuOptions,
                                onOptionClicked = { optionRoute ->
                                    navController.navigate(optionRoute.route)
                                }
                            )
                        }
                        composable(NavRoute.CardScreen.route) {
                            titleText = NavRoute.CardScreen.pageTitle
                            CardScreen()
                        }
                        composable(NavRoute.ButtonScreen.route) {
                            titleText = NavRoute.ButtonScreen.pageTitle
                            ButtonScreen()
                        }
                        composable(NavRoute.EntriesScreen.route) {
                            titleText = NavRoute.EntriesScreen.pageTitle
                            EntriesScreen()
                        }
                    }
                }
            }
        }
    }
}