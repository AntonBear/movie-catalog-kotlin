package com.anton.movie_catalog_kotlin.movieDetailsScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.repository.Repositories


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(onBackClick: () -> Unit, movieId: String) {

    val viewModel: MovieDetailsViewModel = viewModel(
        factory = MovieDetailsViewModelFactory(
            movieId,
            Repositories.movieRepository,
            Repositories.kinopoiskRepository
        )
    )

    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("")},
                navigationIcon = {
                    SvgButtonBack(
                        R.drawable.chevron_left,
                        onClick = {
                            onBackClick()
                            Log.d(
                                "MovieDetailsScreen",
                                "Кнопка \"Назад\" нажата! Movie ID: $movieId"
                            )
                        }
                    )
                },
                actions = {
                    ComposeButtonLike(
                        R.drawable.ic_like,
                        onClick = {
                            Log.d(
                                "MovieDetailsScreen",
                                "Кнопка \"Лайк\" нажата! Movie ID: $movieId"
                            )
                        }
                    )
                },
            )
        }

    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .statusBarsPadding()

            ) {
                when (val state = uiState) {
                    is MovieDetailsUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        )
                    }

                    is MovieDetailsUiState.Success -> {
                        val movieDetails = state.data.movieDetails
                        val kinopoiskDetails = state.data.filmDetails

                        AsyncImage(
                            model = kinopoiskDetails?.posterUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentScale = ContentScale.FillWidth,
                            alignment = Alignment.TopCenter,
                            placeholder = painterResource(id = R.drawable.background),
                            error = painterResource(id = R.drawable.background)
                        )

                        GradientText(
                            movieDetails?.name ?: "Название отсутствует",
                            kinopoiskDetails?.slogan ?: "Слоган отсутствует"
                        )

                        RoundedTextElement(
                            text = kinopoiskDetails?.description ?: "Описание отсутствует"
                        )

                        ReviewField(
                            R.drawable.ic_star,
                            "Рейтинг",
                            kinopoiskDetails = kinopoiskDetails
                        )


                    }

                    is MovieDetailsUiState.Error -> {
                        Text(
                            "Error: ${state.errorType}",
                            Modifier.padding(16.dp)
                        )
                    }
                }

            }
        }
    }
}





