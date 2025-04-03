package com.hoker.supra.presentation.scaffolds

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hoker.supra.presentation.sizes.Sizes

@Composable
fun SupraGyroScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    drawerContent: (@Composable () -> Unit)? = null,
    drawerWidth: Dp = Sizes.defaultDrawerWidth,
    isDrawerOpen: Boolean = false,
    borderColor: Color,
    backgroundColor: Color,
    content: @Composable (modifier: Modifier) -> Unit
) {
    val viewModel: GyroScaffoldViewModel = hiltViewModel()
    val translationData by viewModel.translationData.collectAsState()

    val xOffset = translationData.second
    val yOffset = translationData.first

    val animatedXOffset = animateDpAsState(targetValue = xOffset.dp, label = "")
    val animatedYOffset = animateDpAsState(targetValue = yOffset.dp, label = "")

    val drawerOffset by animateDpAsState(
        targetValue = if (drawerContent != null && isDrawerOpen) drawerWidth else 0.dp,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(borderColor)
    ) {
        if (drawerContent != null) {
            Box(
                modifier = Modifier
                    .width(drawerWidth)
                    .statusBarsPadding()
                    .fillMaxHeight()
                    .background(borderColor)
                    .offset(x = drawerOffset - drawerWidth)
            ) {
                Column {
                    drawerContent()
                }
            }
        }
        Scaffold(
            modifier = Modifier
                .statusBarsPadding()
                .offset(x = drawerOffset),
            topBar = {
                topBar?.invoke()
            },
            bottomBar = {
                bottomBar?.invoke()
            },
            containerColor = borderColor
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = borderColor
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(x = animatedXOffset.value, y = animatedYOffset.value)
                            .padding(vertical = 16.dp, horizontal = 8.dp)
                            .shadow(elevation = 3.dp, shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp)),
                        color = backgroundColor
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
                }
            }
        }
    }
}