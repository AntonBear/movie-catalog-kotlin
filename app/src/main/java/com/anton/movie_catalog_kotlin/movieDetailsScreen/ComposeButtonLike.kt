package com.anton.movie_catalog_kotlin.movieDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.anton.movie_catalog_kotlin.R

@Composable
fun ComposeButtonLike(
    resourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isMovieFavorite: Boolean
) {
    var showGradient by remember { mutableStateOf(false) }
    val gradient = Brush.horizontalGradient(
        listOf(colorResource(id = R.color.start_orange_gradient), colorResource(id = R.color.end_orange_gradient))
    )


    LaunchedEffect(isMovieFavorite) {
        showGradient = isMovieFavorite
    }


    IconButton(
        onClick = {
            onClick()
            showGradient = !showGradient
        },
        modifier = if(showGradient) modifier.background(gradient, RoundedCornerShape(8.dp)) else modifier.background(MaterialTheme.colorScheme.onSecondary, RoundedCornerShape(8.dp))
    ){
        Icon(
            painter = painterResource(id = resourceId),
            contentDescription = null,
            modifier = Modifier.padding(4.dp)
        )
    }
}