package com.anton.movie_catalog_kotlin.networking

import com.anton.movie_catalog_kotlin.models.LoginResponse
import com.anton.movie_catalog_kotlin.models.SignUpRequest
import models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MovieCatalogApi {
    @POST("api/account/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<LoginResponse>

    @POST("api/account/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}

