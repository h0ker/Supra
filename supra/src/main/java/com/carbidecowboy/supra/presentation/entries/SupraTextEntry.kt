package com.carbidecowboy.supra.presentation.entries

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.carbidecowboy.supra.utils.ModifierUtils.Companion.innerShadow

@Composable
fun SupraTextEntry(
    modifier: Modifier = Modifier,
    value: String,
    roundedCornerShape: Dp = 20.dp,
    backgroundColor: Color = Color(0xffe5e5e5),
    textStyle: TextStyle = TextStyle.Default,
    onValueChanged: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .innerShadow(
                shape = RoundedCornerShape(roundedCornerShape),
                color = Color.White,
                offsetX = (-2).dp,
                offsetY = (-2).dp,
                blur = 6.dp,
            )
            .innerShadow(
                shape = RoundedCornerShape(roundedCornerShape),
                color = Color.Black.copy(0.5f),
                offsetX = 2.dp,
                offsetY = 2.dp,
                blur = 6.dp,
            )
            .padding(16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusRequester.requestFocus()
            }
        ,
    ) {
            BasicTextField(
                value = value,
                textStyle = textStyle,
                onValueChange = onValueChanged,
                 keyboardActions = KeyboardActions(onDone = { focusRequester.freeFocus() }),
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .focusTarget()
            )
    }
}