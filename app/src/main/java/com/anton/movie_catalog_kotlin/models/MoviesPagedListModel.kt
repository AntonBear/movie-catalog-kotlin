package com.anton.movie_catalog_kotlin.models
import kotlinx.serialization.Serializable

@Serializable
data class MoviesPagedListModel(
    val movies: List<MovieElementModel>,
    val pageInfo: PageInfoModel,
)

@Serializable
data class MovieElementModel(
    val id: String? = null,
    val name: String? = null,
    val poster: String? = null,
    val year: Int? = null,
    val country: String? = null,
    val genres: List<GenreModel> = emptyList(),
    val reviews: List<ReviewShortModel> = emptyList()
)

@Serializable
data class GenreModel(
    val id: String? = null,
    val name: String? = null
)

@Serializable
data class ReviewShortModel(
    val id: String? = null,
    val rating: Int? = null
)

@Serializable
data class PageInfoModel(
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int
)