package com.anton.movie_catalog_kotlin.models_old

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val token: String
)
