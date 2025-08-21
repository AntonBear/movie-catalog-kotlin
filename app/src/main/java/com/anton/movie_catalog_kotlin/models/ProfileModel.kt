package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

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

