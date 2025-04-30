package com.hoker.supra.presentation.scaffolds

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hoker.supra.domain.LoadingState
import com.hoker.supra.presentation.indicators.IndeterminateLoadingIndicator
import com.hoker.supra.presentation.indicators.ScanIndicator
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.snackbars.SupraSnackbar

@Composable
fun SupraGyroScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    backgroundColor: Color,
    surfaceColor: Color,
    content: @Composable (modifier: Modifier) -> Unit
) {
    val viewModel: GyroScaffoldViewModel = hiltViewModel()
    val loadingState = viewModel.supraFX.loadingState.collectAsState()

    //Gyro effect
    val translationData by viewModel.translationData.collectAsState()
    val xOffset = translationData.second
    val yOffset = translationData.first
    val animatedXOffset = animateDpAsState(targetValue = xOffset.dp, label = "")
    val animatedYOffset = animateDpAsState(targetValue = yOffset.dp, label = "")

    //Pulse effect
    var currentEffect by remember { mutableStateOf<Color?>(null) }
    val effectProgress = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    viewModel.supraFX.oneshotChannel.let { channel ->
        LaunchedEffect(channel) {
            for (effect in channel) {
                currentEffect = effect
                effectProgress.snapTo(0f)
                alpha.snapTo(1f)
                effectProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = LinearEasing
                    )
                )
                alpha.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = FastOutLinearInEasing
                    )
                )
                currentEffect = null
            }
        }
    }

    viewModel.supraFX.snackbarChannel.let { channel ->
        LaunchedEffect(channel) {
            for (message in channel) {
                snackbarMessage = message
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .drawBehind {
                currentEffect?.let { effect ->
                    val maxRadius = size.height.toDouble().toFloat() * 3f
                    val currentRadius = effectProgress.value * maxRadius

                    if (currentRadius > 0) {
                        val brush = Brush.radialGradient(
                            colorStops = arrayOf(
                                0f to backgroundColor,
                                0.2f to backgroundColor,
                                0.5f to effect.copy(alpha = alpha.value),
                                0.8f to backgroundColor,
                                1f to backgroundColor
                            ),
                            center = center,
                            radius = currentRadius
                        )

                        drawCircle(
                            brush = brush,
                            radius = currentRadius,
                            center = center
                        )
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                topBar?.invoke()
            },
            bottomBar = {
                bottomBar?.invoke()
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = animatedXOffset.value, y = animatedYOffset.value)
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .padding(paddingValues)
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                Surface(
                    modifier = Modifier.matchParentSize(),
                    color = surfaceColor
                ) {
                    content(
                        Modifier
                            .offset(
                                x = animatedXOffset.value,
                                y = animatedYOffset.value
                            )
                            .padding(8.dp)
                    )
                }
                IndeterminateLoadingIndicator(
                    show = loadingState.value == LoadingState.LOADING_INDETERMINATE,
                )
                ScanIndicator(
                    loadingState = loadingState.value,
                    onCancelClicked = {
                        viewModel.supraFX.setLoadingState(LoadingState.INACTIVE)
                    }
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .padding(
                    start = Sizes.medium,
                    end = Sizes.medium,
                    bottom = Sizes.xLarge
                )
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            visible = snackbarMessage != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            SupraSnackbar(
                message = snackbarMessage ?: "",
                onDismiss = {
                    snackbarMessage = null
                },
                backgroundColor = MaterialTheme.colorScheme.background,
                borderColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}