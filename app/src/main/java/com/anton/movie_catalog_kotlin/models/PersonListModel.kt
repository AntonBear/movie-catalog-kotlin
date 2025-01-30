package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable

@Serializable
data class PersonListModel(
    val total: Int,
    val items: List<PersonItem>
)

@Serializable
data class PersonItem(
    val kinopoiskId: Int,
    val webUrl: String,
    val nameRu: String,
    val nameEn: String,
    val sex: String,
    val posterUrl: String? = null
)