package com.hoker.supra.presentation.indicators

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import com.hoker.supra.R
import com.hoker.supra.domain.OverlayState
import com.hoker.supra.presentation.buttons.SupraOutlinedButton
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.text.SupraTitleTextMedium
import kotlinx.coroutines.delay

@Composable
fun ScanIndicator(
    overlayState: OverlayState,
    scanningColor: Color = MaterialTheme.colorScheme.tertiary,
    customContent: (@Composable () -> Unit)?,
    onCancelClicked: () -> Unit
) {

    AnimatedVisibility(
        visible = overlayState == OverlayState.SCAN_PROMPT || overlayState == OverlayState.SCAN_PROMPT_CUSTOM_CONTENT || overlayState == OverlayState.CUSTOM_CONTENT,
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
                .animateContentSize()
                .fillMaxWidth()
                .padding(horizontal = Sizes.medium),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (overlayState == OverlayState.SCAN_PROMPT_CUSTOM_CONTENT || overlayState == OverlayState.CUSTOM_CONTENT) {
                customContent?.let { content ->
                    content()
                }
            }
            if (overlayState == OverlayState.SCAN_PROMPT_CUSTOM_CONTENT || overlayState == OverlayState.SCAN_PROMPT) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SupraTitleTextMedium(
                        text = stringResource(R.string.scan_device_desc),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    SupraOutlinedButton(
                        onClick = {
                            onCancelClicked()
                        },
                        text = stringResource(R.string.cancel),
                        modifier = Modifier.padding(top = Sizes.large),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
    AnimatedVisibility(
        visible = overlayState == OverlayState.SCANNING,
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
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}