package com.anton.movie_catalog_kotlin.movieDetailsScreen

import com.anton.movie_catalog_kotlin.models.FilmDetails
import com.anton.movie_catalog_kotlin.models.MovieDetailsModel

data class MovieDetailsCombined(
    val movieDetails: MovieDetailsModel? = null,
    val filmDetails: FilmDetails? = null
)
