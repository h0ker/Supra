package com.hoker.supraexample.domain.models

sealed class NavRoute(val route: String, val pageTitle: String) {
    data object HomeScreen: NavRoute("home_screen", "Home")
    data object MaterialScreen: NavRoute("material_screen", "Materials")
    data object TextureScreen: NavRoute("texture_screen", "Textures")
    data object ButtonScreen: NavRoute("button_screen", "Buttons")
    data object ListItemScreen: NavRoute("list_item_screen", "List Items")
    data object EntriesScreen: NavRoute("entries_screen", "Entries")
    data object FullscreenEntryScreen: NavRoute("fullscreen_entry_screen", "Fullscreen Entry")
    data object PickerScreen: NavRoute("picker_screen", "Pickers")
    data object DialogScreen: NavRoute("dialog_screen", "Dialogs")
    data object SnackbarScreen: NavRoute("snackbar_screen", "Snackbars")
    data object IndicatorScreen: NavRoute("indicator_screen", "Indicators")
    data object ScanScreen: NavRoute("scan_screen", "Scan")
    data object SupraFXScreen: NavRoute("suprafx_screen", "SupraFX")
    data object ChromeScreen: NavRoute("chrome_screen", "Chrome")
    data object ColorScreen: NavRoute("color_screen", "Colors")
    data object TextScreen: NavRoute("text_screen", "Text")
    data object QrScannerScreen: NavRoute("qr_scanner_screen", "QR Scanner")

    companion object {
        val menuOptions = listOf(
            MaterialScreen,
            TextureScreen,
            ButtonScreen,
            ListItemScreen,
            EntriesScreen,
            FullscreenEntryScreen,
            PickerScreen,
            DialogScreen,
            SnackbarScreen,
            IndicatorScreen,
            ScanScreen,
            SupraFXScreen,
            ChromeScreen,
            ColorScreen,
            TextScreen,
            QrScannerScreen
        )
    }
}
