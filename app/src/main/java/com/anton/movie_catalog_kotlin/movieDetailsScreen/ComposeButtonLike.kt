package com.anton.movie_catalog_kotlin.movieDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ComposeButtonLike(
    resourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {

    val icon = painterResource(id = resourceId)

    IconButton(onClick = onClick, modifier = modifier.clip(RoundedCornerShape(8.dp))
        .background(MaterialTheme.colorScheme.onSecondary)) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = Color.White
        )
    }

}