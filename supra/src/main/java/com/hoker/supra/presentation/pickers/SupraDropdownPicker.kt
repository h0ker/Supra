package com.hoker.supra.presentation.pickers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.interaction_sources.NoRippleInteractionSource
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraBodyTextSmall

///TODO: Unfinished
@Composable
fun SupraDropdownPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedValue: String,
    label: String? = null,
    labelBackgroundColor: Color = MaterialTheme.colorScheme.background,
    height: Dp = Sizes.defaultDropdownPickerHeight,
    width: Dp = Sizes.defaultDropdownPickerWidth,
    onTouchAction: () -> Unit = {},
    onItemSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .height(height)
            .width(width)
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = if(label != null) 8.dp else 0.dp),
            onClick = {
                expanded = true
                onTouchAction.invoke()
            },
            interactionSource = NoRippleInteractionSource(),
            border = BorderStroke(if(expanded) 2.dp else 1.dp, if(expanded) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary),
            shape = RoundedCornerShape(4.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SupraBodyTextSmall(
                    text = selectedValue,
                )
                Icon(
                    imageVector = Icons.Filled.ExpandMore,
                    contentDescription = "dropdown icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        if(label != null) {
            Box(
                modifier = Modifier
                    .offset(x = (12).dp, y = (-4).dp)
                    .background(labelBackgroundColor)
                    .padding(start = 4.dp, end = 4.dp)
            ) {
                SupraBodyTextSmall(
                    text = label
                )
            }
        }
        DropdownMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { value ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onItemSelected(value)
                    },
                    text = {
                        SupraBodyTextSmall(text = value)
                    }
                )
            }
        }
    }
}