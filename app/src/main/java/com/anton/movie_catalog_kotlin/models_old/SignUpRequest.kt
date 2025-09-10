package com.anton.movie_catalog_kotlin.models_old

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val gender: Int,
)