package com.hoker.supra.presentation.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.text.SupraBodyTextSmall
import com.hoker.supra.utils.ColorUtils.contrastingTextColor

enum class MultiSelectorOption {
    Option,
    Background
}

@Composable
fun SupraMultiSelector(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String,
    onOptionSelect: (String) -> Unit,
    state: MultiSelectorState = rememberMultiSelectorState(
        options = options,
        selectedOption = selectedOption,
        selectedColor = Color.Black,
        unselectedColor = Color.White
    )
) {
    require(options.size >= 2) { "This composable requires at least 2 options" }
    require(options.contains(selectedOption)) { "Invalid selected option [$selectedOption]" }

    LaunchedEffect(
        key1 = options,
        key2 = selectedOption
    ) {
        state.selectOption(this, options.indexOf(selectedOption))
    }

    Layout(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surface),
        content = {
            options.forEachIndexed { index, option ->
                val isSelected = option == selectedOption
                val backgroundColor = if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface
                val targetTextColor = backgroundColor.contrastingTextColor()
                val animatedTextColor by animateColorAsState(targetValue = targetTextColor)
                Box(
                    modifier = Modifier
                        .layoutId(MultiSelectorOption.Option)
                        .clickable { onOptionSelect(option) },
                    contentAlignment = Alignment.Center
                ) {
                    SupraBodyTextSmall(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = option,
                        color = animatedTextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Box(
                modifier = Modifier
                    .layoutId(MultiSelectorOption.Background)
                    .clip(
                        shape = RoundedCornerShape(
                            topStartPercent = state.startCornerPercent,
                            bottomStartPercent = state.startCornerPercent,
                            topEndPercent = state.endCornerPercent,
                            bottomEndPercent = state.endCornerPercent
                        )
                    )
                    .background(MaterialTheme.colorScheme.tertiary)
            )
        }
    ) { measurables, constraints ->
        val optionWidth = constraints.maxWidth / options.size
        val optionConstraints = Constraints.fixed(
            width = optionWidth,
            height = constraints.maxHeight
        )
        val optionPlaceables = measurables
            .filter { measurable -> measurable.layoutId == MultiSelectorOption.Option }
            .map { measurable -> measurable.measure(optionConstraints) }

        val backgroundPlaceable = measurables
            .first { measurable -> measurable.layoutId == MultiSelectorOption.Background }
            .measure(optionConstraints)

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            backgroundPlaceable.placeRelative(
                x = (state.selectedIndex * optionWidth).toInt(),
                y = 0
            )
            optionPlaceables.forEachIndexed { index, placeable ->
                run {
                    placeable.placeRelative(
                        x = optionWidth * index,
                        y = 0
                    )
                }
            }
        }
    }
}