package com.hoker.supraexample.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraBodyTextLarge
import com.hoker.supra.presentation.text.SupraBodyTextMedium
import com.hoker.supra.presentation.text.SupraBodyTextSmall
import com.hoker.supra.presentation.text.SupraTitleTextLarge
import com.hoker.supra.presentation.text.SupraTitleTextMedium
import com.hoker.supra.presentation.text.SupraTitleTextSmall

@Composable
fun TextScreen() {
    Column(
        modifier = Modifier
            .padding(Sizes.defaultPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Sizes.medium)
    ) {
        //Title text
        SupraTitleTextLarge(
            text = "Title Text Large"
        )
        SupraTitleTextMedium(
            text = "Title Text Medium"
        )
        SupraTitleTextSmall(
            text = "Title Text Small"
        )

        //Body text
        SupraBodyTextSmall(
            text = "Body Text Small"
        )
        SupraBodyTextMedium(
            text = "Body Text Medium"
        )
        SupraBodyTextLarge(
            text = "Body Text Large"
        )
    }
}