package com.anton.movie_catalog_kotlin.networking

import com.anton.movie_catalog_kotlin.models_old.FilmDetails
import com.anton.movie_catalog_kotlin.models_old.MovieSearchModel
import com.anton.movie_catalog_kotlin.models_old.PersonListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("api/v2.1/films/search-by-keyword")
    suspend fun searchMovies(@Query("keyword") keyword: String): Response<MovieSearchModel>


    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmDetails(@Path("id") id: Int): Response<FilmDetails>


    @GET("/api/v1/persons")
    suspend fun getPersonList(@Query("name") name: String): Response<PersonListModel>

}

