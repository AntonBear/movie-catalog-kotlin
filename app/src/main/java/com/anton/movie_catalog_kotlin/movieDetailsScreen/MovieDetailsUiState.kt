package com.anton.movie_catalog_kotlin.movieDetailsScreen



sealed class MovieDetailsUiState {
    data object Loading : MovieDetailsUiState()
    data class Success(val data: MovieDetailsCombined,
                       val isFavorite: Boolean = false,
    ) : MovieDetailsUiState()
    data class Error(val errorType: ErrorType) : MovieDetailsUiState()

}