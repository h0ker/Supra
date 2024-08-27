package com.hoker.supraexample.domain.models

sealed class NavRoutes(val route: String) {
    data object CardScreen: NavRoutes("card_screen")
    data object HomeScreen: NavRoutes("home_screen")
    data object ButtonScreen: NavRoutes("button_screen")
}