package com.hoker.supra.presentation.pickers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoker.supra.presentation.interaction_sources.NoRippleInteractionSource
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.theme.Ink3
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.Ink5
import com.hoker.supra.presentation.theme.Ink7

/**
 * @param labelBackgroundColor Must match the surface behind the picker: the label is a notch, not a chip.
 */
@Composable
fun SupraDropdownPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedValue: String,
    label: String? = null,
    labelBackgroundColor: Color = MaterialTheme.colorScheme.background,
    height: Dp = Sizes.dropdownHeight,
    width: Dp = Sizes.defaultDropdownPickerWidth,
    onTouchAction: () -> Unit = {},
    onItemSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.width(width)
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            onClick = {
                expanded = true
                onTouchAction.invoke()
            },
            interactionSource = NoRippleInteractionSource(),
            border = BorderStroke(if (expanded) 2.dp else 1.dp, if (expanded) MaterialTheme.colorScheme.secondary else Ink5),
            shape = SupraShapes.control,
            contentPadding = PaddingValues(start = 14.dp, end = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = selectedValue,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
                    color = Ink7,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    imageVector = Icons.Filled.ExpandMore,
                    contentDescription = "dropdown icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        if (label != null) {
            Text(
                modifier = Modifier
                    .offset(x = 10.dp, y = (-6).dp)
                    .background(labelBackgroundColor)
                    .padding(horizontal = 5.dp),
                text = label.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, lineHeight = 12.sp),
                color = if (expanded) MaterialTheme.colorScheme.secondary else Ink5
            )
        }
        DropdownMenu(
            shape = SupraShapes.control,
            containerColor = Ink3,
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { value ->
                DropdownMenuItem(
                    modifier = Modifier.background(if (value == selectedValue) Ink4 else Color.Transparent),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 11.dp),
                    onClick = {
                        expanded = false
                        onItemSelected(value)
                    },
                    text = {
                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 15.sp),
                            color = Ink7
                        )
                    }
                )
            }
        }
    }
}
