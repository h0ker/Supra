package com.hoker.supra.presentation.entries

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.theme.Ink5
import com.hoker.supra.presentation.theme.Ink7

/**
 * Outlined field with a notched mono label. 48dp tall with 16sp value text: the size of a real top-bar
 * entry. For text that is the task (keys, payloads, notes) use [SupraFullscreenTextField], never a grown field.
 *
 * @param rows Visible lines when [singleLine] is false.
 * @param labelBackgroundColor Must match the surface behind the field: the label is a notch, not a chip.
 */
@Composable
fun SupraTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String? = null,
    placeholder: String? = null,
    singleLine: Boolean = false,
    rows: Int = 1,
    enabled: Boolean = true,
    focusedBorderColor: Color = MaterialTheme.colorScheme.secondary,
    unfocusedBorderColor: Color = Ink5,
    labelBackgroundColor: Color = MaterialTheme.colorScheme.background,
    visualTransformation: VisualTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: (@Composable () -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()

    Box(
        modifier = modifier.alpha(if (enabled) 1f else .38f)
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = Sizes.controlHeight),
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = singleLine,
            minLines = if (singleLine) 1 else rows,
            maxLines = if (singleLine) 1 else Int.MAX_VALUE,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = Ink7,
                fontSize = 16.sp,
                lineHeight = 1.4.em
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary),
            visualTransformation = visualTransformation ?: VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = Sizes.controlHeight)
                        .border(
                            width = if (focused) 2.dp else 1.dp,
                            color = if (focused) focusedBorderColor else unfocusedBorderColor,
                            shape = SupraShapes.control
                        )
                        .padding(horizontal = 14.dp, vertical = if (singleLine) 0.dp else 12.dp),
                    verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(Sizes.small)
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty() && placeholder != null) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp, lineHeight = 1.4.em),
                                color = Ink5,
                                maxLines = 1
                            )
                        }
                        innerTextField()
                    }
                    trailingIcon?.invoke()
                }
            }
        )
        label?.let {
            Text(
                modifier = Modifier
                    .offset(x = 10.dp, y = (-6).dp)
                    .background(labelBackgroundColor)
                    .padding(horizontal = 5.dp),
                text = it.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, lineHeight = 12.sp),
                color = if (focused) focusedBorderColor else Ink5
            )
        }
    }
}
