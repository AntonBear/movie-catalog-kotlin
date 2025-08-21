package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserShortModel(
    val userId: String,
    val nickName: String? = null,
    val avatar: String? = null
)



