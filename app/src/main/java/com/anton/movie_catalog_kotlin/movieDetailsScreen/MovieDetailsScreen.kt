package com.anton.movie_catalog_kotlin.movieDetailsScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.repository.Repositories
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailsScreen(onBackClick: () -> Unit, movieId: String) {

    val viewModel: MovieDetailsViewModel = viewModel(
        factory = MovieDetailsViewModelFactory(
            movieId,
            Repositories.movieRepository,
            Repositories.kinopoiskRepository,
            Repositories.reviewRepository,
            Repositories.favoriteMovieRepository,
            Repositories.profileRepository,
            Repositories.genreRepository
        )
    )
    var showReviewDialog by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

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
            val listState = rememberLazyListState()
            var currentTopBarTitle by remember { mutableStateOf(movieDetails?.name ?: "") }
            val imageURL = viewModel.directorPoster.collectAsState()
            val movieDetailsUiState by viewModel.uiState.collectAsState()
            val movieGenres by viewModel.movieGenres.observeAsState(emptyList())
            val favoriteGenres by viewModel.favoriteGenres.collectAsState()
            val topBarTitle by remember(currentTopBarTitle) {
                derivedStateOf { currentTopBarTitle }
            }
            var showText by remember { mutableStateOf(false) }

            LaunchedEffect(listState.firstVisibleItemIndex) {
                showText = listState.firstVisibleItemIndex > 0
            }


            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                        title = {
                            Text(
                                text = if (showText) topBarTitle else " ",
                                style = MaterialTheme.typography.bodyMedium.copy(),
                                modifier = Modifier,
                                color = Color.White,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                fontFamily = FontFamily(Font(R.font.manrope_bold)),
                                fontSize = 24.sp
                            )
                        },

                        navigationIcon = {
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
                        },

                        actions = {
                            ComposeButtonLike(
                                resourceId = if (state.isFavorite) R.drawable.ic_like_able else R.drawable.ic_like,
                                onClick = {
                                    viewModel.changeFavoriteMovieHandler()
                                },
                                modifier = Modifier.padding(horizontal = 8.dp),
                                isMovieFavorite = state.isFavorite
                            )
                        },
                    )
                }
            )

            { innerPadding ->
                kinopoiskDetails?.posterUrl?.let { posterUrl ->
                    AsyncImage(
                        model = posterUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .padding(innerPadding)
                            .offset(y = -60.dp)
                            .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)),
                        contentScale = ContentScale.FillWidth,
                        alignment = Alignment.TopCenter,
                        placeholder = painterResource(id = R.drawable.background),
                        error = painterResource(id = R.drawable.background)
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    state = listState
                ) {
                    item {

                        Spacer(modifier = Modifier.height(325.dp))

                    }
                    item {

                    }

                    item {
                        GradientText(
                            movieDetails?.name ?: "Название отсутствует",
                            kinopoiskDetails?.slogan ?: "Слоган отсутствует",
                        )


                    }

                    item {
                        RoundedTextElement(
                            text = kinopoiskDetails?.description ?: "Описание отсутствует"
                        )
                    }

                    item {
                        RatingField(
                            R.drawable.ic_star,
                            "Рейтинг",
                            kinopoiskDetails = kinopoiskDetails
                        )
                        ComposeInfoField(
                            R.drawable.ic_info,
                            "Информация",
                            kinopoiskDetails = kinopoiskDetails,
                            movieDetails = movieDetails
                        )

                        DirectorField(
                            iconResId = R.drawable.ic_director,
                            text = "Режиссеёр",
                            directorName = movieDetails?.director,
                            imageURL = imageURL.value
                        )

                        ReviewField(
                            movieDetails,
                            showReviewDialog,
                            { showReviewDialog = true },
                            viewModel
                        )

                        if (showReviewDialog) {
                            ReviewDialog(
                                viewModel = viewModel,
                                onDismiss = { showReviewDialog = false },
                            )

                        }


                    }

                }

            }
        }
    }
}
















