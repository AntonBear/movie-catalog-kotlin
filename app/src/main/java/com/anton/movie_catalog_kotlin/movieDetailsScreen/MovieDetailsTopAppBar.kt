package com.anton.movie_catalog_kotlin.movieDetailsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.anton.movie_catalog_kotlin.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    posterUrl: String?,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit
) {
    SubcomposeLayout(Modifier.fillMaxWidth()) { constraints ->
        val appBarPlaceable = subcompose("appBar") {
            CenterAlignedTopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                scrollBehavior = scrollBehavior,
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }[0].measure(constraints)

        val imageHeight = appBarPlaceable.height

        val imagePlaceable = subcompose("image") {
            posterUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight.toDp()),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter
                )
            }
        }.firstOrNull()?.measure(constraints.copy(maxHeight = imageHeight))

        layout(constraints.maxWidth, appBarPlaceable.height) {
            imagePlaceable?.placeRelative(0, 0)
            appBarPlaceable.placeRelative(0, 0)
        }
    }
}

