package com.hoker.supraexample.domain.models

/**
 * Scaffold furniture toggles, driven from the Chrome screen.
 */
data class ChromeSettings(
    val brackets: Boolean = true,
    val cornerTitle: Boolean = true,
    val rail: Boolean = true,
    val railStamp: Boolean = true,
    val railMeter: Boolean = true,
    val customTopBar: Boolean = false
)
