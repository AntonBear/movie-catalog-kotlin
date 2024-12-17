package com.anton.movie_catalog_kotlin.models
import kotlinx.serialization.Serializable

@Serializable
data class MoviesPagedListModel(
    val movies: List<MovieElementModel>,
    val pageInfo: PageInfoModel,
)

@Serializable
data class MovieElementModel(
    val id: String,
    val name: String,
    val poster: String,
    val year: Int,
    val country: String,
    val genres: List<GenreModel> = emptyList(),
    val reviews: List<ReviewShortModel> = emptyList()
)

@Serializable
data class GenreModel(
    val id: String,
    val name: String
)

@Serializable
data class ReviewShortModel(
    val id: String,
    val rating: Int
)

@Serializable
data class PageInfoModel(
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int
)