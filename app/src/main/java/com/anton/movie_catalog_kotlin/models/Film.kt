package com.anton.movie_catalog_kotlin.models


import kotlinx.serialization.Serializable

@Serializable
data class Film(
    val filmId: Int,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val type: String?,
    val year: String?,
    val description: String? = null,
    val filmLength: String? = null,
    val countries: List<Country>? = null,
    val genres: List<Genre>,
    val rating: String?,
    val ratingVoteCount: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?,

    )

@Serializable
data class Country(val country: String)

@Serializable
data class Genre(val genre: String)