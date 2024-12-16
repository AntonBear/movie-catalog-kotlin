package com.anton.movie_catalog_kotlin.models

//sealed interface ImageSource {
//    data class Remote(val url: String) : ImageSource
//    data class Local(val id: Int) : ImageSource
//}

data class MovieDetails(
    val id: String,
    val name: String,
    val poster: String,
    val year: Int,
    val country: String,
    val genres: List<String?> = emptyList()
)