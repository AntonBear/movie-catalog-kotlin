package com.anton.movie_catalog_kotlin.movieDetailsScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.repository.Repositories


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailsScreen(onBackClick: () -> Unit, movieId: String) {

    val viewModel: MovieDetailsViewModel = viewModel(
        factory = MovieDetailsViewModelFactory(
            movieId,
            Repositories.movieRepository,
            Repositories.kinopoiskRepository,
            Repositories.reviewRepository,
            Repositories.favoriteMovieRepository
        )
    )
    var showReviewDialog by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val isMovieFavorite by viewModel.isMovieFavorite.collectAsState()

    when (val state = uiState) {
        is MovieDetailsUiState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.wrapContentSize(Alignment.Center)
            )
        }

        is MovieDetailsUiState.Error -> {
            Text(
                "Error: ${state.errorType}",
                Modifier.padding(16.dp)
            )
        }

        is MovieDetailsUiState.Success -> {
            val movieDetails = state.data.movieDetails
            val kinopoiskDetails = state.data.filmDetails
            var gradientTextVisible by remember { mutableStateOf(true) }
            var toolbarText by remember {
                mutableStateOf(
                    movieDetails?.name ?: "Название отсутствует"
                )
            }
            var windowHeightPx by remember { mutableStateOf(0) }
            var myElementCoordinates by remember { mutableStateOf<Offset?>(null) }


            LaunchedEffect(gradientTextVisible) {
                toolbarText = if (gradientTextVisible) {
                    ""
                } else {
                    movieDetails?.name ?: "Название отсутствует"
                }
                println("gradientTextVisible changed: $gradientTextVisible")

            }

            Scaffold(
                topBar = {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp )
                    ) {
                        kinopoiskDetails?.posterUrl?.let { posterUrl ->
                            AsyncImage(
                                model = posterUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(500.dp)
                                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)),
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.TopCenter
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {

                            SvgButtonBack(
                                resourceId = R.drawable.ic_chevron_left,
                                onClick = {
                                    onBackClick()
                                    Log.d(
                                        "MovieDetailsScreen",
                                        "Кнопка \"Назад\" нажата! Movie ID: $movieId"
                                    )
                                },
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            Text(
                                text = "toolbarText",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier,
                                color = Color.Blue,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )

                            ComposeButtonLike(
                                resourceId = if(isMovieFavorite) R.drawable.ic_like_able  else R.drawable.ic_like,
                                onClick = {
                                    viewModel.changeFavoriteMovieHandler()
                                    Log.d(
                                        "MovieDetailsScreen",
                                        "Кнопка \"Лайк\" нажата! Movie ID: $movieId"
                                    )
                                },
                                modifier = Modifier.padding(horizontal = 8.dp),
                                isMovieFavorite = isMovieFavorite
                            )
                        }
                    }
                }



            ) { innerPadding ->


            Box(modifier = Modifier
                    .background(color = colorResource(R.color.dark))
                    .onGloballyPositioned { coordinates ->
                        windowHeightPx = coordinates.size.height
                        println(windowHeightPx)
                    }
                    ) {
                    kinopoiskDetails?.posterUrl?.let { posterUrl ->
                        AsyncImage(
                            model = posterUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
                                .padding(innerPadding)
                                .offset(y = -60.dp)
                                .align(Alignment.TopCenter)
                                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)),
                            contentScale = ContentScale.FillWidth,
                            alignment = Alignment.TopCenter,
                            placeholder = painterResource(id = R.drawable.background),
                            error = painterResource(id = R.drawable.background)
                        )
                    }


                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(innerPadding)
                    ) {
                        Spacer(modifier = Modifier.height(350.dp))

                        GradientText(
                            movieDetails?.name ?: "Название отсутствует",
                            kinopoiskDetails?.slogan ?: "Слоган отсутствует",
                            modifier = Modifier.onGloballyPositioned { coordinates ->
                                myElementCoordinates = coordinates.localToWindow(Offset.Zero)

                                if (windowHeightPx > 0) {
                                    println(coordinates.localToWindow(Offset.Zero))
                                    val visible =
                                        coordinates.localToWindow(Offset.Zero).y + coordinates.size.height < windowHeightPx && coordinates.localToWindow(
                                            Offset.Zero
                                        ).y > 0
                                    gradientTextVisible = visible
                                }
                            }
                        )
                        LaunchedEffect(myElementCoordinates) {
                            println("Координаты изменились: $myElementCoordinates")
                        }

                        if (showReviewDialog) {
                            ReviewDialog(
                                movieId = movieId,
                                viewModel = viewModel,
                                onDismiss = { showReviewDialog = false },
                                onAnonymousChange = { viewModel.onAnonCheckedChange(it) },

                                )

                        }

                        RoundedTextElement(
                            text = kinopoiskDetails?.description ?: "Описание отсутствует"
                        )

                        RatingField(
                            R.drawable.ic_star,
                            "Рейтинг",
                            kinopoiskDetails = kinopoiskDetails
                        )


                        ReviewField(
                            movieDetails,
                            showReviewDialog,
                            { showReviewDialog = true },
                            viewModel
                        )
                    }

                }
            }
        }
    }
}
















