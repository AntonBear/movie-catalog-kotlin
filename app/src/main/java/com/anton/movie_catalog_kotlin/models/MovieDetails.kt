package com.anton.movie_catalog_kotlin.models

import com.anton.movie_catalog_kotlin.models_old.GenreModel

data class MovieDetails(
    val id: String,
    val name: String,
    val poster: String,
    val year: Int,
    val country: String,
    val genres: List<GenreModel> = emptyList(),
)
