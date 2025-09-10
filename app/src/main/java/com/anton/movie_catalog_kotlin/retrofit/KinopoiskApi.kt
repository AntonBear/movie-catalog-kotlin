package com.anton.movie_catalog_kotlin.retrofit

import com.anton.movie_catalog_kotlin.models_old.FilmDetails
import com.anton.movie_catalog_kotlin.models_old.MovieSearchModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("api/v2.1/films/search-by-keyword")
    suspend fun searchByKeyword(
        @Header("X-API-KEY") apiKey: String,
        @Query("keyword") keyword: String,
    ): MovieSearchModel

    @GET("/api/v2.2/films")
    suspend fun getFilmDetails(
        @Header("X-API-KEY") apiKey: String,
        @Query("id") id: Int,
    ): FilmDetails
}