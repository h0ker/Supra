package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.pickers.SupraDropdownPicker
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraBodyTextMedium
import com.hoker.supraexample.presentation.models.UiTheme
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ColorScreen(
    themeInitialState: String,
    onThemeStateChanged: (String) -> Unit
) {

    var themeSelection by remember { mutableStateOf(themeInitialState) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Sizes.medium),
        verticalArrangement = Arrangement.spacedBy(Sizes.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SupraBodyTextMedium(
                modifier = Modifier.padding(start = 16.dp),
                text = "Theme"
            )
            SupraDropdownPicker(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .width(140.dp)
                    .height(50.dp),
                items = UiTheme.entries.map { it.title },
                labelBackgroundColor = MaterialTheme.colorScheme.background,
                selectedValue = themeSelection,
                label = "Theme",
                onTouchAction = {
                }
            ) { themeTitle ->
                themeSelection = themeTitle
                onThemeStateChanged(themeTitle)
            }
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