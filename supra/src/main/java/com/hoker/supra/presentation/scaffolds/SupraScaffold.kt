package com.hoker.supra.presentation.scaffolds

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun SupraScaffold(
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    borderColor: Color,
    backgroundColor: Color,
    content: @Composable (modifier: Modifier) -> Unit
){
    Scaffold (
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
        ){
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp, horizontal = 8.dp)
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp)),
                color = backgroundColor
            ){
                content( Modifier.padding(8.dp) )
            }
        }
    }
}