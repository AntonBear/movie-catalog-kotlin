package com.anton.movie_catalog_kotlin.movieDetailsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.navigation.findNavController


@Composable
fun MovieDetailsScreen() {

    val navController = LocalView.current.findNavController()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Button(onClick = {}) {
            Text("MovieDetailsScreen")
        }
    }
}