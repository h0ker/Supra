package com.hoker.supra.presentation.entries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.theme.ErrorRed
import com.hoker.supra.presentation.theme.Ink2
import com.hoker.supra.presentation.theme.Ink3
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.Ink5
import com.hoker.supra.presentation.theme.Ink7

/**
 * Max-area text entry, for when the text is the task: HMAC keys, NDEF payloads, notes, pasted blobs.
 *
 * - One per screen; give it the whole content surface (it fills what it's given).
 * - Mono by default, since the content is usually machine data.
 * - Counter, box and left edge turn red past [maxBytes]. The caret is the only accent in a resting field.
 * - Put the committing button in [action], pinned to the field, not floating above the keyboard.
 *
 * @param byteCount Show a UTF-8 byte counter in the top-right corner.
 * @param counter Overrides the byte counter text.
 */
@Composable
fun SupraFullscreenTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String? = null,
    placeholder: String? = null,
    mono: Boolean = true,
    byteCount: Boolean = false,
    maxBytes: Int? = null,
    counter: String? = null,
    enabled: Boolean = true,
    action: (@Composable () -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    var focused by remember { mutableStateOf(false) }
    val bytes = remember(value) { value.toByteArray(Charsets.UTF_8).size }
    val over = maxBytes != null && bytes > maxBytes
    val accent = MaterialTheme.colorScheme.secondary
    val edge = when {
        over -> ErrorRed
        focused -> accent
        else -> Ink4
    }
    val box = when {
        over -> ErrorRed
        focused -> accent
        else -> Ink2
    }
    val meta = counter ?: if (byteCount) "$bytes${maxBytes?.let { " / $it" } ?: ""} BYTES" else null

    Column(
        modifier = modifier
            .fillMaxSize()
            .alpha(if (enabled) 1f else .38f)
            .clip(SupraShapes.control)
            .background(Ink3)
            .drawWithContent {
                drawContent()
                val hairline = 1.dp.toPx()
                // Double width because the clip trims the outer half, leaving a 1dp line that follows the corners
                drawOutline(
                    outline = SupraShapes.control.createOutline(size, layoutDirection, this),
                    color = box,
                    style = Stroke(width = hairline * 2)
                )
                drawRect(edge, size = Size(3.dp.toPx(), size.height))
            }
    ) {
        if (label != null || meta != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 12.dp, top = 10.dp, bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label.orEmpty().uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Ink5
                )
                meta?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        color = when {
                            over -> ErrorRed
                            focused -> accent
                            else -> Ink5
                        }
                    )
                }
            }
        }
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(start = 15.dp, end = 12.dp, top = 2.dp, bottom = 12.dp)
                .onFocusChanged { focused = it.isFocused },
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            textStyle = (if (mono) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodyMedium)
                .copy(color = Ink7, fontSize = if (mono) 14.sp else 16.sp, lineHeight = 22.sp),
            cursorBrush = SolidColor(accent),
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                            color = Ink5
                        )
                    }
                    innerTextField()
                }
            }
        )
        action?.let {
            HorizontalDivider(color = Ink2)
            Box(Modifier.padding(start = 11.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)) { it() }
        }
    }
}
