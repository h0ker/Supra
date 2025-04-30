package com.hoker.supraexample.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hoker.supra.presentation.buttons.SupraHardwareButton
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.utils.ColorUtils.contrastingTextColor

@Composable
fun ButtonScreen(
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }

    //Example Hardware Buttons
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(Sizes.medium)
    ) {
        SupraHardwareButton(
            modifier = Modifier
                .size(100.dp),
            text = "Text",
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.primary.contrastingTextColor()
        ) {}
        SupraHardwareButton(
            modifier = Modifier.size(64.dp),
            text = "Text",
            backgroundColor = Color.LightGray,
            textColor = Color.LightGray.contrastingTextColor()
        ) {}
        SupraHardwareButton(
            modifier = Modifier
                .height(70.dp)
                .width(150.dp),
            outerCornerRadius = 16.dp,
            innerCornerRadius = 8.dp ,
            text = "Text",
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            textColor = MaterialTheme.colorScheme.tertiary.contrastingTextColor()
        ) {}
    }
}