package com.anton.movie_catalog_kotlin.movieDetailsScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.anton.movie_catalog_kotlin.ui.theme.MoviecatalogkotlinTheme


class MovieDetailsScreenView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviecatalogkotlinTheme {
            }
        }
    }
}