package com.anton.movie_catalog_kotlin.movieDetailsScreen

import com.anton.movie_catalog_kotlin.models.FilmDetails
import com.anton.movie_catalog_kotlin.models.MovieDetailsModel


sealed class MovieDetailsUiState {
    object Loading : MovieDetailsUiState()
    data class Success(val data: MovieDetailsCombined) : MovieDetailsUiState()
    data class Error(val errorType: ErrorType) : MovieDetailsUiState()

}