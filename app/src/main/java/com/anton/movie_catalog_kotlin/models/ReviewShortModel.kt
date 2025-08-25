package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable


@Serializable
data class ReviewShortModel(
    val id: String,
    val rating: Int,
    val isAnonymous: Boolean? = null,
)