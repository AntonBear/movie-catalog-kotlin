package com.anton.movie_catalog_kotlin.networking

import com.anton.movie_catalog_kotlin.models.LoginResponse
import com.anton.movie_catalog_kotlin.models.MovieDetails
import com.anton.movie_catalog_kotlin.models.MoviesPagedListModel
import com.anton.movie_catalog_kotlin.models.SignUpRequest
import models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MovieCatalogApi {
    @POST("api/account/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<LoginResponse>

    @POST("api/account/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/api/movies/{page}")
    suspend fun getMovies(@Path("page") page: Int): Response<MoviesPagedListModel>

    @GET("/api/movies/details/{id}")
    suspend fun getDetails(@Path("id") id: Int): Response<MovieDetails>
}

