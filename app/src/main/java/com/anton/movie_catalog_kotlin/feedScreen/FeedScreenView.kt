package com.anton.movie_catalog_kotlin.feedScreen

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.models.ImageSource
import com.anton.movie_catalog_kotlin.navigationBar.NavigationBarViewModel

class FeedScreenViewModelProvider : PreviewParameterProvider<FeedScreenViewModel> {
    override val values = sequenceOf(FeedScreenViewModel(MovieRepositoryStub()))
}

@Preview
@Composable
fun FeedScreenPreview(@PreviewParameter(FeedScreenViewModelProvider::class) feedScreenViewModel: FeedScreenViewModel) {
    FeedScreenView(
        object : FeedScreenDelegate {},
        navController = rememberNavController(),
        feedScreenViewModel
    )
}

interface FeedScreenDelegate {
    fun onMovieClick() {}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedScreenView(
    delegate: FeedScreenDelegate,
    navController: NavController,
    feedScreenViewModel: FeedScreenViewModel = viewModel(factory = FeedScreenViewModel.Factory)
) {
    val movieDetails by feedScreenViewModel.movieDetails.collectAsState()
    val isLoading by feedScreenViewModel.isLoading.collectAsState()

    Scaffold(
        containerColor = colorResource(id = R.color.backgroundColor)
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                AndroidView(
                    factory = { context ->
                        ImageView(context).apply {
                            setImageResource(R.drawable.ic_launcher_feedscreen)
                            scaleType = ImageView.ScaleType.CENTER
                        }
                    }
                )

                when (val poster = movieDetails.poster) {
                    is ImageSource.Remote ->

                        AsyncImage(
                            model = poster.url,
                            contentDescription = "Movie Poster",
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(NavigationBarViewModel.Screen.MovieScreen.route)
                                }
                                .fillMaxWidth()
                                .padding(
                                    start = 20.dp,
                                    top = 10.dp,
                                    end = 20.dp,
                                    bottom = 10.dp
                                )
                                .clip(RoundedCornerShape(30.dp)),
                            placeholder = painterResource(R.drawable.background),
                            error = painterResource(R.drawable.error_image),
                            fallback = painterResource(R.drawable.error_image),
                            contentScale = ContentScale.Fit
                        )


                    is ImageSource.Local -> Image(
                        painter = painterResource(R.drawable.background),
                        contentDescription = "Movie Poster",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(
                                start = 20.dp,
                                top = 0.dp,
                                end = 20.dp,
                                bottom = 0.dp
                            )
                            .clip(RoundedCornerShape(20.dp)),
                    )
                }
            }

            Text(
                movieDetails.name,
                color = Color.White,
                modifier = Modifier.wrapContentHeight(),
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.manrope_bold)),
                style = TextStyle(textAlign = TextAlign.Center),
                overflow = TextOverflow.Ellipsis,
            )

            Row(
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    "${movieDetails.country} • ${movieDetails.year}",
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.gray),
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 5.dp,
                        end = 16.dp,
                        bottom = 5.dp
                    )
                )
            }

            LazyRow {
                items(
                    minOf(3, movieDetails.genres.size),
                    key = { index -> index }) { index ->
                    val genre =
                        movieDetails.genres.getOrNull(index)
                    if (genre != null) {
                        Chip(
                            onClick = { /* ... */ },
                            modifier = Modifier
                                .padding(start = 5.dp, top = 0.dp, end = 0.dp, bottom = 0.dp),

                            colors = ChipDefaults.chipColors(backgroundColor = colorResource(id = R.color.dark_faded))
                        ) {
                            Text(text = genre, color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}




