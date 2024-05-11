package com.carbidecowboy.supraexample.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carbidecowboy.supra.presentation.surface.SupraConvexSurface
import com.carbidecowboy.supraexample.domain.models.NavRoutes

@Composable
fun SurfaceScreen() {
    Column {
        SupraConvexSurface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(120.dp),
            backgroundColor = Color.DarkGray
        ) { }
        SupraConvexSurface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(.5f)
                .height(60.dp),
            backgroundColor = Color.DarkGray,
            cornerRadius = 16.dp
        ) { }
    }
}