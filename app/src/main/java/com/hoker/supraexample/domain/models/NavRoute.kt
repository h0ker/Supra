package com.hoker.supraexample.domain.models

sealed class NavRoute(val route: String, val pageTitle: String) {
    data object HomeScreen: NavRoute("home_screen", "SUPRA")
    data object CardScreen: NavRoute("card_screen", "Cards")
    data object ButtonScreen: NavRoute("button_screen", "Buttons")
    data object EntriesScreen: NavRoute("entries_screen", "Entries")

    companion object {
        val menuOptions = listOf(
            CardScreen,
            ButtonScreen,
            EntriesScreen
        )
    }
}