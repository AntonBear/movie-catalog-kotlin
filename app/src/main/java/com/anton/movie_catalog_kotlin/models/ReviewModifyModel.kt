package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable


@Serializable
data class ReviewModifyModel(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean
)