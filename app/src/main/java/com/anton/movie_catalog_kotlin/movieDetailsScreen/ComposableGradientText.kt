package com.anton.movie_catalog_kotlin.movieDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.anton.movie_catalog_kotlin.R

@Composable
fun GradientText(text: String, desc: String) {
    val gradient = Brush.horizontalGradient(
        listOf(colorResource(id = R.color.start_orange_gradient), colorResource(id = R.color.end_orange_gradient))
    )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(gradient, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
        }
    }
