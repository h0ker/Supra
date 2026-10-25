package com.hoker.supra.presentation.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.text.SupraBodyTextSmall
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.Ink5
import com.hoker.supra.utils.ColorUtils.contrastingTextColor
import com.hoker.supra.utils.ColorUtils.inkOn

enum class MultiSelectorOption {
    Option,
    Background
}

enum class MultiSelectorShape {
    /** Hard-edged segments with mono uppercase labels. */
    BLOCK,
    /** The original rounded pill. */
    PILL
}

@Composable
fun SupraMultiSelector(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String,
    onOptionSelect: (String) -> Unit,
    shape: MultiSelectorShape = MultiSelectorShape.BLOCK,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedBackgroundColor: Color = if (shape == MultiSelectorShape.BLOCK) Color.Transparent else MaterialTheme.colorScheme.surface,
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

    val isBlock = shape == MultiSelectorShape.BLOCK

    Layout(
        modifier = modifier
            .clip(if (isBlock) SupraShapes.control else SupraShapes.pill)
            .background(unselectedBackgroundColor)
            .then(if (isBlock) Modifier.border(1.dp, Ink4, SupraShapes.control) else Modifier),
        content = {
            options.forEach { option ->
                val isSelected = option == selectedOption
                val targetTextColor = if (isBlock) {
                    if (isSelected) selectedBackgroundColor.inkOn() else Ink5
                } else {
                    (if (isSelected) selectedBackgroundColor else unselectedBackgroundColor).contrastingTextColor()
                }
                val animatedTextColor by animateColorAsState(targetValue = targetTextColor)
                Box(
                    modifier = Modifier
                        .layoutId(MultiSelectorOption.Option)
                        .clickable { onOptionSelect(option) },
                    contentAlignment = Alignment.Center
                ) {
                    if (isBlock) {
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = option.uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            letterSpacing = 0.12.em,
                            textAlign = TextAlign.Center,
                            color = animatedTextColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        SupraBodyTextSmall(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = option,
                            color = animatedTextColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .layoutId(MultiSelectorOption.Background)
                    .clip(
                        shape = if (isBlock) {
                            SupraShapes.control
                        } else {
                            RoundedCornerShape(
                                topStartPercent = state.startCornerPercent,
                                bottomStartPercent = state.startCornerPercent,
                                topEndPercent = state.endCornerPercent,
                                bottomEndPercent = state.endCornerPercent
                            )
                        }
                    )
                    .background(selectedBackgroundColor)
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
