package com.hoker.supraexample.domain.models

sealed class NavRoute(val route: String, val pageTitle: String) {
    data object HomeScreen: NavRoute("home_screen", "SUPRA")
    data object TextureScreen: NavRoute("texture_screen", "Textures")
    data object ButtonScreen: NavRoute("button_screen", "Buttons")
    data object EntriesScreen: NavRoute("entries_screen", "Entries")
    data object SupraFXScreen: NavRoute("suprafx_screen", "SupraFX")
    data object ColorScreen: NavRoute("color_screen", "Colors")
    data object DialogScreen: NavRoute("dialog_screen", "Dialogs")
    data object TextScreen: NavRoute("text_screen", "Text")
    data object PickerScreen: NavRoute("picker_screen", "Pickers")
    data object QrScannerScreen: NavRoute("qr_scanner_screen", "QR Scanner")

    companion object {
        val menuOptions = listOf(
            TextureScreen,
            ButtonScreen,
            EntriesScreen,
            SupraFXScreen,
            ColorScreen,
            DialogScreen,
            TextScreen,
            PickerScreen,
            QrScannerScreen
        )
    }
}