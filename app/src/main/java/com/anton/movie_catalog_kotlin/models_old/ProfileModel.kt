package com.anton.movie_catalog_kotlin.models_old

import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel(
    val id: String,
    val nickName: String? = null,
    val email: String,
    val avatarLink: String? = null,
    val name: String,
    val birthDate: String,
    val gender: Gender
)

