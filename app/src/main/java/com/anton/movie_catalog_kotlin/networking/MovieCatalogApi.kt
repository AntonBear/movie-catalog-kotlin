package com.anton.movie_catalog_kotlin.networking

import com.anton.movie_catalog_kotlin.models.ErrorResponse
import com.anton.movie_catalog_kotlin.models.LoginResponse
import com.anton.movie_catalog_kotlin.models.SignUpRequest
import models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MovieCatalogApi {
    @POST("api/account/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<Unit>

    @POST("api/account/login")
    suspend fun login(@Body loginRequest: LoginRequest): NetworkResult<LoginResponse, ErrorResponse>
}

