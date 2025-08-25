package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val token: String
)
