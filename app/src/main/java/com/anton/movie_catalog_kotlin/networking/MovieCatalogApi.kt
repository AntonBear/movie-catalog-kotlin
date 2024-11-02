package com.anton.movie_catalog_kotlin.networking

import com.anton.movie_catalog_kotlin.models.ErrorResponse
import com.anton.movie_catalog_kotlin.models.LoginResponse
import com.anton.movie_catalog_kotlin.models.SingUpRequest
import com.anton.movie_catalog_kotlin.models.SingUpResponse
import models.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MovieCatalogApi {
    @POST("api/account/register")
    suspend fun singUp(@Body singUpRequest: SingUpRequest): Response<SingUpResponse>

    @POST("api/account/login")
    suspend fun login(@Body loginRequest: LoginRequest): NetworkResult<LoginResponse, ErrorResponse>

}

