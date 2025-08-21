package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable


@Serializable
data class ReviewModel(
    val id: String,
    val rating: Int,
    val reviewText: String? = null,
    val isAnonymous: Boolean,
    val createDateTime: String,
    val author: UserShortModel
)