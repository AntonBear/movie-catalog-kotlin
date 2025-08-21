package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable

@Serializable
data class MovieSearchModel(
    val keyword: String,
    val pagesCount: Int,
    val films: List<Film>,
    val searchFilmsCountResult: Int,
    )
