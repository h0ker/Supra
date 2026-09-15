package com.hoker.supraexample.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.controls.SupraMultiSelector
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraBodyTextMedium
import com.hoker.supra.presentation.text.SupraDisplayBlock
import com.hoker.supra.presentation.theme.Ink5

/**
 * Standard screen body: scrollable, `medium` padding, `medium` spacing.
 */
@Composable
fun ScreenColumn(
    modifier: Modifier = Modifier,
    spacing: Dp = Sizes.medium,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Sizes.medium),
        verticalArrangement = Arrangement.spacedBy(spacing),
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

/**
 * Mono caption naming what a specimen shows.
 */
@Composable
fun SpecimenLabel(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = Ink5
    )
}

/**
 * Label on the left, segmented selector on the right.
 */
@Composable
fun SettingRow(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Sizes.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SupraBodyTextMedium(
            modifier = Modifier.weight(1f),
            text = label
        )
        SupraMultiSelector(
            modifier = Modifier
                .height(36.dp)
                .width(150.dp),
            options = options,
            selectedOption = selectedOption,
            onOptionSelect = onOptionSelect
        )
    }
}

val OnOffOptions = listOf("On", "Off")

fun Boolean.toOnOff() = if (this) "On" else "Off"

/**
 * Display-block wordmark with a mono sub line, for material plates.
 */
@Composable
fun Wordmark(
    modifier: Modifier = Modifier,
    sub: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Sizes.medium),
        verticalArrangement = Arrangement.spacedBy(Sizes.tiny)
    ) {
        SupraDisplayBlock(text = "Supra")
        SpecimenLabel(text = sub)
    }
}
