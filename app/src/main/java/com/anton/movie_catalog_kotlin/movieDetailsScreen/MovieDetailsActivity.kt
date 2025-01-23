package com.anton.movie_catalog_kotlin.movieDetailsScreen

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.anton.movie_catalog_kotlin.signup.SignUpViewModel
import com.anton.movie_catalog_kotlin.ui.theme.MovieCatalogKotlinTheme


class MovieDetailsActivity : ComponentActivity() {

    private val movieId: String by lazy {
        intent?.getStringExtra("id") ?: ""
    }
//    private val viewModel: MovieDetailsViewModel by viewModels { MovieDetailsViewModel.Factory }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                MovieCatalogKotlinTheme {
                    MovieDetailsScreen(onBackClick = { finish() }, movieId = movieId)
                }
            }
    }
}



