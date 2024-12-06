package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserShortModel(
    val userId: String,
    val nickName: String? = null,
    val avatar: String? = null
)

data class ReviewModel(
    val id: String,
    val rating: Int,
    val reviewText: String? = null,
    val isAnonymous: Boolean,
    val createDateTime: LocalDateTime,
    val author: UserShortModel
)

data class MovieDetailsModel(
    val id: String,
    val name: String? = null,
    val poster: String? = null,
    val year: Int,
    val country: String? = null,
    val genres: List<GenreModel>,
    val reviews: List<ReviewModel>,
    val time: Int,
    val tagline: String? = null,
    val description: String? = null,
    val director: String? = null,
    val budget: Int? = null,
    val fees: Int? = null,
    val ageLimit: Int? = null
)

data class ReviewModifyModel(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean
)

data class ProfileModel(
    val id: String,
    val nickName: String? = null,
    val email: String,
    val avatarLink: String? = null,
    val name: String,
    val birthDate: LocalDateTime,
    val gender: Gender
)

