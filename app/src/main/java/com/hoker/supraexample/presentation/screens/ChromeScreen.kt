package com.hoker.supraexample.presentation.screens

import androidx.compose.runtime.Composable
import com.hoker.supra.presentation.surfaces.SupraCollapsibleArea
import com.hoker.supraexample.domain.models.ChromeSettings
import com.hoker.supraexample.presentation.components.OnOffOptions
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SettingRow
import com.hoker.supraexample.presentation.components.SpecimenLabel
import com.hoker.supraexample.presentation.components.toOnOff

/**
 * Scaffold furniture toggles, grouped into collapsible sections. Doubles as the collapsible's demo.
 */
@Composable
fun ChromeScreen(
    settings: ChromeSettings,
    onSettingsChanged: (ChromeSettings) -> Unit
) {
    ScreenColumn {
        SpecimenLabel(text = "Scaffold furniture · applied live")

        SupraCollapsibleArea(
            title = "Brackets",
            status = settings.brackets.toOnOff(),
            initiallyOpen = true
        ) {
            SettingRow(
                label = "Corner brackets",
                options = OnOffOptions,
                selectedOption = settings.brackets.toOnOff(),
                onOptionSelect = { onSettingsChanged(settings.copy(brackets = it == "On")) }
            )
        }

        SupraCollapsibleArea(
            title = "Readout",
            status = settings.cornerTitle.toOnOff()
        ) {
            SettingRow(
                label = "Corner title",
                options = OnOffOptions,
                selectedOption = settings.cornerTitle.toOnOff(),
                onOptionSelect = { onSettingsChanged(settings.copy(cornerTitle = it == "On")) }
            )
        }

        SupraCollapsibleArea(
            title = "Rail",
            status = when {
                !settings.rail -> "Off"
                settings.railMeter -> "Meter"
                else -> "Static"
            }
        ) {
            SettingRow(
                label = "Rail",
                options = OnOffOptions,
                selectedOption = settings.rail.toOnOff(),
                onOptionSelect = { onSettingsChanged(settings.copy(rail = it == "On")) }
            )
            SettingRow(
                label = "Stamp",
                options = OnOffOptions,
                selectedOption = settings.railStamp.toOnOff(),
                onOptionSelect = { onSettingsChanged(settings.copy(railStamp = it == "On")) }
            )
            SettingRow(
                label = "Meter",
                options = OnOffOptions,
                selectedOption = settings.railMeter.toOnOff(),
                onOptionSelect = { onSettingsChanged(settings.copy(railMeter = it == "On")) }
            )
            SpecimenLabel(text = "Magnetometer · ticks carry the reading")
        }

        SupraCollapsibleArea(
            title = "Top bar",
            status = if (settings.customTopBar) "Custom" else "Default"
        ) {
            SettingRow(
                label = "Custom bar",
                options = OnOffOptions,
                selectedOption = settings.customTopBar.toOnOff(),
                onOptionSelect = { onSettingsChanged(settings.copy(customTopBar = it == "On")) }
            )
            SpecimenLabel(text = "Replaces the drawer bar · swipe from the edge to open the drawer")
        }
    }
}
