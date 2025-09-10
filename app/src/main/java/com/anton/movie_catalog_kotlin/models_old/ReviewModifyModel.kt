package com.anton.movie_catalog_kotlin.models_old

import kotlinx.serialization.Serializable


@Serializable
data class ReviewModifyModel(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean
)