package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.Serializable


@Serializable
data class FavoritesMoviesListModel(
    val movies: List<MovieElementModel>
)
