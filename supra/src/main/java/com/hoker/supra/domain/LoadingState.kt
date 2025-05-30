package com.hoker.supra.domain

enum class LoadingState {
    SCANNING,
    LOADING_INDETERMINATE,
    SCAN_PROMPT,
    LOADING_CANCELABLE, //TODO: Add this state
    INACTIVE
}