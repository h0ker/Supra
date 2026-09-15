package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.buttons.SupraHardwareButton
import com.hoker.supra.presentation.list_items.SupraTextureListItem
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.theme.Ink1
import com.hoker.supra.presentation.theme.Ink2
import com.hoker.supra.presentation.theme.Ink3
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.Ink5
import com.hoker.supra.presentation.theme.Ink7
import com.hoker.supra.presentation.theme.UiTheme
import com.hoker.supraexample.presentation.components.ScreenColumn
import com.hoker.supraexample.presentation.components.SpecimenLabel
import kotlin.math.roundToInt

private val neutralRamp = listOf(
    "Ink1" to Ink1,
    "Ink2" to Ink2,
    "Ink3" to Ink3,
    "Ink4" to Ink4,
    "Ink5" to Ink5,
    "Ink7" to Ink7
)

@Composable
fun ColorScreen(
    themeInitialState: String,
    onThemeStateChanged: (String) -> Unit
) {

    var themeSelection by remember { mutableStateOf(UiTheme.fromTitle(themeInitialState)) }

    ScreenColumn {
        SpecimenLabel(text = "Neutral ramp")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(SupraShapes.control)
                .border(1.dp, Ink4, SupraShapes.control)
        ) {
            neutralRamp.forEach { (_, color) ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .background(color)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            neutralRamp.forEach { (name, color) ->
                Column(modifier = Modifier.weight(1f)) {
                    SpecimenLabel(text = name)
                    SpecimenLabel(text = color.toHex().removePrefix("#"))
                }
            }
        }

        SpecimenLabel(text = "Accent swap · applied live · accent = state only")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Sizes.small)
        ) {
            UiTheme.entries.forEach { theme ->
                val selected = theme == themeSelection
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            themeSelection = theme
                            onThemeStateChanged(theme.title)
                        },
                    verticalArrangement = Arrangement.spacedBy(Sizes.tiny)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(theme.accent, SupraShapes.control)
                            .border(2.dp, if (selected) Ink7 else Color.Transparent, SupraShapes.control)
                    )
                    SpecimenLabel(text = theme.title)
                }
            }
        }

        SpecimenLabel(text = "Where the accent is legal")
        SupraTextureListItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(Sizes.defaultListItemHeight),
            title = "Spark 2 · Slot 1",
            status = "Live",
            live = true,
            rightIconImageVector = Icons.Default.ChevronRight
        )
        SupraTextureListItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(Sizes.defaultListItemHeight),
            title = "Spark 2 · Slot 2",
            status = "—",
            rightIconImageVector = Icons.Default.ChevronRight
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpecimenLabel(
                modifier = Modifier.weight(1f),
                text = "The one committing button"
            )
            SupraHardwareButton(
                text = "Commit",
                onClick = {}
            )
        }
    }
}

fun Color.toHex(includeAlpha: Boolean = false): String {
    val a = (alpha * 255).roundToInt()
    val r = (red * 255).roundToInt()
    val g = (green * 255).roundToInt()
    val b = (blue * 255).roundToInt()
    return if (includeAlpha) {
        "#%02X%02X%02X%02X".format(a, r, g, b)
    } else {
        "#%02X%02X%02X".format(r, g, b)
    }
}

fun parseHex(hex: String): Color? {
    try {
        var formatted = hex.replace("#", "")
        if (formatted.length == 6) {
            // If no alpha, default to FF (opaque)
            formatted = "FF" + formatted
        }
        if (formatted.length != 8) return null
        val colorInt = formatted.toLong(16).toInt()
        return Color(colorInt)
    } catch (e: Exception) {
        return null
    }
}
