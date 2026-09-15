package com.hoker.supraexample.presentation.screens

import androidx.compose.runtime.Composable
import com.hoker.supraexample.domain.models.ChromeSettings
import com.hoker.supraexample.presentation.components.OnOffOptions
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SettingRow
import com.hoker.supraexample.presentation.components.SpecimenLabel
import com.hoker.supraexample.presentation.components.toOnOff

@Composable
fun ChromeScreen(
    settings: ChromeSettings,
    onSettingsChanged: (ChromeSettings) -> Unit
) {
    ScreenColumn {
        SpecimenLabel(text = "Scaffold furniture · applied live")
        SettingRow(
            label = "Brackets",
            options = OnOffOptions,
            selectedOption = settings.brackets.toOnOff(),
            onOptionSelect = { onSettingsChanged(settings.copy(brackets = it == "On")) }
        )
        SettingRow(
            label = "Corner title",
            options = OnOffOptions,
            selectedOption = settings.cornerTitle.toOnOff(),
            onOptionSelect = { onSettingsChanged(settings.copy(cornerTitle = it == "On")) }
        )
        SettingRow(
            label = "Rail",
            options = OnOffOptions,
            selectedOption = settings.rail.toOnOff(),
            onOptionSelect = { onSettingsChanged(settings.copy(rail = it == "On")) }
        )
        SettingRow(
            label = "Rail stamp",
            options = OnOffOptions,
            selectedOption = settings.railStamp.toOnOff(),
            onOptionSelect = { onSettingsChanged(settings.copy(railStamp = it == "On")) }
        )
        SettingRow(
            label = "Rail meter",
            options = OnOffOptions,
            selectedOption = settings.railMeter.toOnOff(),
            onOptionSelect = { onSettingsChanged(settings.copy(railMeter = it == "On")) }
        )
        SpecimenLabel(text = "Magnetometer · ticks carry the reading · stamp stays the revision")
        SettingRow(
            label = "Custom top bar",
            options = OnOffOptions,
            selectedOption = settings.customTopBar.toOnOff(),
            onOptionSelect = { onSettingsChanged(settings.copy(customTopBar = it == "On")) }
        )
        SpecimenLabel(text = "The custom bar replaces the drawer bar wholesale · swipe from the edge to open the drawer")
    }
}
