package com.hoker.supra.presentation.controls

enum class SettingsBooleanState(val displayName: String) {
    ENABLED("Enabled"),
    DISABLED("Disabled");

    companion object {
        fun fromString(value: String): SettingsBooleanState {
            return entries.first { it.displayName == value }
        }

        fun fromBoolean(value: Boolean): SettingsBooleanState {
            return if (value) ENABLED else DISABLED
        }
    }
}