package com.anton.movie_catalog_kotlin.models_old

import kotlinx.serialization.Serializable

@Serializable
data class UserShortModel(
    val userId: String,
    val nickName: String? = null,
    val avatar: String? = null
)



