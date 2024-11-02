package com.anton.movie_catalog_kotlin.models

data class Film(
    val filmId: Int,
    val nameRu: String,
    val nameEn: String,
    val rating: String,
    val general: Boolean,
    val description: String,
    val professionKey: String
)