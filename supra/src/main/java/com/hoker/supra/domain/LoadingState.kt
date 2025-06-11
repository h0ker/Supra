package com.hoker.supra.domain

enum class LoadingState {
    SCANNING,
    LOADING_INDETERMINATE,
    SCAN_PROMPT,
    SCAN_PROMPT_CUSTOM_CONTENT,
    LOADING_CANCELABLE, //TODO: Add this state
    INACTIVE
}