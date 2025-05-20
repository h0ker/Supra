package com.hoker.supra.presentation.indicators

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hoker.supra.R
import com.hoker.supra.domain.LoadingState
import com.hoker.supra.presentation.buttons.SupraOutlinedButton
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraTitleTextMedium
import kotlinx.coroutines.delay

@Composable
fun ScanIndicator(
    loadingState: LoadingState,
    scanningColor: Color = MaterialTheme.colorScheme.tertiary,
    onCancelClicked: () -> Unit
) {

    AnimatedVisibility(
        visible = loadingState == LoadingState.SCAN_PROMPT,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent()
                    }
                }
            },
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.5f))
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(bottom = Sizes.medium)
                    .size(Sizes.xLarge),
                painter = painterResource(R.drawable.contactless),
                contentDescription = "contactless icon",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            SupraTitleTextMedium(
                text = stringResource(R.string.scan_device_desc),
                color = MaterialTheme.colorScheme.primary
            )
            SupraOutlinedButton(
                onClick = {
                    onCancelClicked()
                },
                text = stringResource(R.string.cancel),
                modifier = Modifier.padding(top = Sizes.medium),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
    AnimatedVisibility(
        visible = loadingState == LoadingState.SCANNING,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent()
                    }
                }
            },
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.5f))
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var dotCount by remember { mutableIntStateOf(0) }
            LaunchedEffect(Unit) {
                while (true) {
                    dotCount = (dotCount + 1) % 4
                    delay(500)
                }
            }
            RandomLoadingGlyph(
                modifier = Modifier.padding(Sizes.large),
                isLoading = true,
                activeColor = scanningColor
            )
            Box(
                contentAlignment = Alignment.Center
            ) {
                SupraTitleTextMedium(
                    text = stringResource(R.string.hold_still) + ".".repeat(3),
                    color = Color.Transparent
                )
                SupraTitleTextMedium(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = stringResource(R.string.hold_still) + ".".repeat(dotCount),
                    color = scanningColor
                )
            }
        }
    }
}