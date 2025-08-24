package com.anton.movie_catalog_kotlin.retrofit

import com.anton.movie_catalog_kotlin.models.LoginResponse
import models.LoginRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface KreosoftApi {
    @Headers("Content-Type: application/json")
    @POST("api/account/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}