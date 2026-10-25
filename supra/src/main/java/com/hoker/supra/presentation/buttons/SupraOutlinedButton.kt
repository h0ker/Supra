package com.hoker.supra.presentation.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.text.SupraBodyTextMedium
import com.hoker.supra.utils.ColorUtils.inkOn
import com.hoker.supra.presentation.theme.Ink5

/**
 * Hairline outlined button. Set [primary] for the one committing action on a screen:
 * it fills with the accent and is the only button allowed to carry it.
 */
@Composable
fun SupraOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    borderColor: Color = Ink5,
    primary: Boolean = false
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = SupraShapes.control,
        border = if (primary) null else BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (primary) MaterialTheme.colorScheme.secondary.inkOn() else color,
            containerColor = if (primary) MaterialTheme.colorScheme.secondary else Color.Transparent
        )
    ) {
        if (primary) {
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.08.em,
                color = MaterialTheme.colorScheme.secondary.inkOn()
            )
        } else {
            SupraBodyTextMedium(
                text = text,
                color = color
            )
        }
    }
}
