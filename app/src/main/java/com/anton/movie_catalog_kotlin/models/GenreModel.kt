package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable

@Serializable
data class GenreModel(
    val id: String,
    val name: String
)