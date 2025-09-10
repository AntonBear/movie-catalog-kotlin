package com.anton.movie_catalog_kotlin.movieDetailsScreen

import com.anton.movie_catalog_kotlin.models_old.FilmDetails
import com.anton.movie_catalog_kotlin.models_old.MovieDetailsModel

data class MovieDetailsCombined(
    val movieDetails: MovieDetailsModel? = null,
    val filmDetails: FilmDetails? = null
)
