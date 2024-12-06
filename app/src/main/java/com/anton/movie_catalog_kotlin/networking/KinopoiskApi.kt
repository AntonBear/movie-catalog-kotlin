package com.anton.movie_catalog_kotlin.networking

import com.anton.movie_catalog_kotlin.models.Staff
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface KinopoiskApi {
    @GET("api/v1/staff/{filmId}")
    suspend fun getStaff(@Path("filmId") filmId: Int): Response<Staff>
}