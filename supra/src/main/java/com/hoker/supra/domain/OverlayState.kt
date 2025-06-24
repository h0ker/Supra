package com.hoker.supra.domain

enum class OverlayState {
    SCANNING,
    LOADING_INDETERMINATE,
    SCAN_PROMPT,
    SCAN_PROMPT_CUSTOM_CONTENT,
    CUSTOM_CONTENT,
    LOADING_CANCELABLE, //TODO: Add this state
    INACTIVE
}