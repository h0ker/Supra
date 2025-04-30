package com.hoker.supra.presentation.list_items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraTitleTextMedium

@Composable
fun SupraColoredButtonListItem(
    modifier: Modifier = Modifier,
    text: String,
    height: Dp = Sizes.defaultListItemHeight,
    width: Dp = Sizes.defaultListItemWidth,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(height)
            .width(width)
            .clickable(
                onClick = {
                    onClick()
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .width(160.dp)
                .height(height)
                .clip(
                    RoundedCornerShape(
                        topStart = height / 2,
                        bottomStart = height / 2
                    )
                ),
            color = MaterialTheme.colorScheme.primary
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                SupraTitleTextMedium(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    text = text,
                    textAlignment = TextAlign.Center
                )
            }
        }
        Surface(
            modifier = Modifier
                .width(height)
                .height(height)
                .clip(
                    RoundedCornerShape(
                        topEnd = height / 2,
                        bottomEnd = height / 2
                    )
                ),
            color = Color.Yellow
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Chevron Right",
                tint = Color.Black
            )
        }
    }
}