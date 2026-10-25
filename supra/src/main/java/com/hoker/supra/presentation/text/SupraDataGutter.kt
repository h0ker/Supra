package com.hoker.supra.presentation.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.theme.DataBlue
import com.hoker.supra.presentation.theme.Ink4
import com.hoker.supra.presentation.theme.Ink5

/**
 * A narrow column of mono key/value readouts. Values must be real data, never decoration.
 */
@Composable
fun SupraDataGutter(
    modifier: Modifier = Modifier,
    rows: List<Pair<String, String>>,
    width: Dp = 104.dp,
    divider: Boolean = true
) {
    Column(
        modifier = modifier
            .width(width)
            .then(
                if (divider) {
                    Modifier
                        .drawBehind {
                            drawLine(Ink4, Offset.Zero, Offset(0f, size.height), 1.dp.toPx())
                        }
                        .padding(start = 10.dp)
                } else {
                    Modifier
                }
            ),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        rows.forEach { (key, value) ->
            Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text(
                    text = key.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Ink5
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.labelMedium,
                    color = DataBlue,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
