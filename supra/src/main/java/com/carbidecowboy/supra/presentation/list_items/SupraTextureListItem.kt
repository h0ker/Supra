package com.carbidecowboy.supra.presentation.list_items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.carbidecowboy.supra.presentation.cards.SupraTextureCard
import com.carbidecowboy.supra.presentation.cards.TextureType

@Composable
fun SupraTextureListItem(
    modifier: Modifier = Modifier,
    title: String,
    leftIconId: Int? = null,
    rightIconId: Int? = null,
    leftIconImageVector: ImageVector? = null,
    rightIconImageVector: ImageVector? = null,
    textureType: TextureType,
    backgroundColor: Color,
    tint: Color,
    onClick: (() -> Unit)? = null
) {
    SupraTextureCard(
        modifier = modifier
            .clickable {
                onClick?.invoke()
            },
        textureType = textureType,
        backgroundColor = backgroundColor,
        tint = tint
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                leftIconId?.let { iconId ->
                    Icon(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        painter = painterResource(id = iconId),
                        contentDescription = "left icon"
                    )
                }
                leftIconImageVector?.let { vector ->
                    Icon(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        imageVector = vector,
                        contentDescription = "left icon"
                    )
                }
                Text(
                    text = title
                )
            }
            rightIconId?.let { iconId ->
                Icon(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    painter = painterResource(id = iconId),
                    contentDescription = "right icon"
                )
            }
            rightIconImageVector?.let { vector ->
                Icon(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    imageVector = vector,
                    contentDescription = "right icon"
                )
            }
        }
    }
}