package com.anton.movie_catalog_kotlin.models_old

import kotlinx.serialization.Serializable


@Serializable
data class ReviewShortModel(
    val reviewText: String? = null,
    val rating: Int,
    val isAnonymous: Boolean? = null,
)