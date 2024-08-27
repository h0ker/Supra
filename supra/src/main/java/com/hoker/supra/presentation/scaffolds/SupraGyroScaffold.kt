package com.hoker.supra.presentation.scaffolds

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SupraGyroScaffold(
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
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

    Scaffold(
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