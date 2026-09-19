package com.hoker.supra.presentation.scaffolds

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoker.supra.domain.OverlayState
import com.hoker.supra.presentation.cards.darkenColor
import com.hoker.supra.presentation.indicators.IndeterminateLoadingIndicator
import com.hoker.supra.presentation.indicators.ScanIndicator
import com.hoker.supra.presentation.shapes.SupraShapes
import com.hoker.supra.presentation.sizes.Sizes
import com.hoker.supra.presentation.snackbars.SupraSnackbar
import com.hoker.supra.utils.ModifierUtils.Companion.magneticGlow
import kotlin.random.Random

@Composable
fun SupraFXScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    borderColor: Color,
    contentBackgroundColor: Color,
    background: SupraBackground? = null,
    brackets: Boolean = true,
    title: String? = null,
    readout: Pair<String?, String?>? = null,
    rail: Boolean = false,
    railStamp: String? = null,
    railMeter: Boolean = false,
    content: @Composable (modifier: Modifier) -> Unit
) {
    val viewModel: GyroScaffoldViewModel = viewModel()
    val loadingState = viewModel.supraFX.overlayState.collectAsState()
    val loadingStateCustomContent = viewModel.supraFX.overlayCustomContent.collectAsState()

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

    // Derive the tint/fallback color for backgrounds
    val defaultTint = darkenColor(borderColor, .2f)

    // Remember texture resources to keep them stable across recompositions
    val textureRotation = remember { Random.nextInt(2) * 180f }
    val textureResId = remember(background) {
        (background as? SupraBackground.Texture)?.textureType?.getTextureId()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(borderColor)
            .drawBehind {
                currentEffect?.let { effect ->
                    val maxRadius = size.height.toDouble().toFloat() * 3f
                    val currentRadius = effectProgress.value * maxRadius

                    if (currentRadius > 0) {
                        val brush = Brush.radialGradient(
                            colorStops = arrayOf(
                                0f to borderColor,
                                0.2f to borderColor,
                                0.5f to effect.copy(alpha = alpha.value),
                                0.8f to borderColor,
                                1f to borderColor
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
        // Background layer (bottommost)
        when (background) {
            is SupraBackground.Texture -> {
                if (textureResId != null) {
                    val textureTint = background.tint ?: defaultTint
                    Image(
                        painter = painterResource(textureResId),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(textureTint),
                        modifier = Modifier
                            .graphicsLayer {
                                rotationZ = textureRotation
                            }
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            is SupraBackground.AnimatedGrid -> {
                AnimatedGridBackground(
                    config = background,
                    fallbackColor = defaultTint,
                    gyroXOffset = xOffset,
                    gyroYOffset = yOffset
                )
            }
            is SupraBackground.DiagonalStripes -> {
                DiagonalStripesBackground(
                    config = background,
                    fallbackColor = defaultTint
                )
            }
            null -> { /* No background */ }
        }
        // Bezel padding matches the bracket insets (4dp top; left, right and bottom derived from the display's
        // corner radius), so the corner title, rail and content surface all sit inside the same box as the
        // brackets, clear of rounded screen corners and cutouts.
        val bezelPadding = rememberBracketInset()
        if (brackets) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .supraBrackets(MaterialTheme.colorScheme.secondary, inset = bezelPadding)
            )
        }
        val bezelPaddingTop = SupraChromeDefaults.BracketInsetTop
        val cornerTitle = title ?: readout?.first
        val hasCorner = cornerTitle != null || readout?.second != null
        val hasTopBar = topBar != null || hasCorner
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                if (hasCorner) {
                    // Bracket furniture sits above the top bar, so it takes over the status bar inset.
                    // The top bar stays its own row underneath.
                    Column(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal))
                            .padding(start = bezelPadding, end = bezelPadding, top = bezelPaddingTop)
                    ) {
                        SupraBracketTitle(
                            title = cornerTitle,
                            index = readout?.second
                        )
                        // 16dp keeps the bracket register (title, index) separate from the app's own bar
                        topBar?.let {
                            Box(modifier = Modifier.padding(top = 16.dp)) { it() }
                        }
                    }
                } else if (topBar != null) {
                    Box(
                        modifier = Modifier.padding(horizontal = bezelPadding)
                    ) {
                        topBar()
                    }
                }
            },
            bottomBar = {
                if (rail) {
                    // Furniture is always outermost: app furniture sits above the rail, and nothing an
                    // app supplies renders below it or outside the bracket box
                    Column {
                        val railModifier = Modifier
                            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                            .padding(horizontal = bezelPadding)
                        bottomBar?.let {
                            Box(modifier = railModifier.padding(top = 8.dp)) { it() }
                        }
                        if (railMeter) {
                            MagnetometerRail(
                                modifier = railModifier,
                                stamp = railStamp
                            )
                        } else {
                            SupraRail(
                                modifier = railModifier,
                                stamp = railStamp
                            )
                        }
                        Spacer(
                            Modifier
                                .padding(top = bezelPadding)
                                .windowInsetsBottomHeight(WindowInsets.systemBars)
                        )
                    }
                } else {
                    bottomBar?.invoke()
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = animatedXOffset.value, y = animatedYOffset.value)
                    .padding(
                        start = bezelPadding,
                        end = bezelPadding,
                        top = if (hasTopBar) 8.dp else bezelPaddingTop,
                        bottom = when {
                            rail -> 0.dp // the rail, and a bottom bar above it, carry their own 8dp top padding
                            bottomBar != null -> 8.dp
                            else -> bezelPadding
                        }
                    )
                    .padding(paddingValues)
                    .shadow(elevation = 3.dp, shape = SupraShapes.surface)
                    .clip(SupraShapes.surface),
            ) {
                Surface(
                    modifier = Modifier
                        .blur(if (loadingState.value != OverlayState.INACTIVE) 20.dp else 0.dp)
                        .matchParentSize(),
                    color = contentBackgroundColor
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
                    show = loadingState.value == OverlayState.LOADING_INDETERMINATE,
                )
                ScanIndicator(
                    overlayState = loadingState.value,
                    customContent = loadingStateCustomContent.value,
                    onCancelClicked = {
                        viewModel.supraFX.overlayDismissAction.value?.invoke()
                        viewModel.supraFX.setLoadingState(OverlayState.INACTIVE)
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
                }
            )
        }
    }
}
/**
 * The rail driven by the magnetometer: the one genuinely live instrument on the bezel. The sensor only
 * listens while this is composed, and falls back to the static rail on devices without a magnetometer.
 */
@Composable
private fun MagnetometerRail(
    modifier: Modifier = Modifier,
    stamp: String?
) {
    val magnetometer: MagneticFieldViewModel = viewModel()

    if (!magnetometer.hasSensor) {
        SupraRail(modifier = modifier, stamp = stamp)
        return
    }

    DisposableEffect(magnetometer) {
        magnetometer.start()
        onDispose { magnetometer.stop() }
    }

    val field by magnetometer.normalisedField.collectAsState()
    val level by animateFloatAsState(targetValue = field, label = "rail_level")

    SupraRail(
        modifier = modifier,
        stamp = stamp,
        level = level
    )
}
