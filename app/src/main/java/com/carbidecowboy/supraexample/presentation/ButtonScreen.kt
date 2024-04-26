package com.carbidecowboy.supraexample.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carbidecowboy.supra.presentation.buttons.SupraHardwareButton

@Composable
fun ButtonScreen() {
    //Example Hardware Buttons
    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        Arrangement.SpaceBetween
    ) {
        SupraHardwareButton(
            modifier = Modifier
                .size(100.dp),
            buttonText = "GB",
            backgroundColor = Color(0xFFE9D30E),
        ) {}
        SupraHardwareButton(
            modifier = Modifier
                .size(64.dp),
            outerCornerRadius = 4.dp,
            innerCornerRadius = 4.dp ,
            buttonText = "Text",
            backgroundColor = Color(0xFF04B4BA),
        ) {}
        SupraHardwareButton(
            modifier = Modifier
                .height(70.dp)
                .width(150.dp),
            outerCornerRadius = 16.dp,
            innerCornerRadius = 8.dp ,
            buttonText = "Text",
            backgroundColor = Color(0xFFD8D4CF),
        ) {}
    }
}