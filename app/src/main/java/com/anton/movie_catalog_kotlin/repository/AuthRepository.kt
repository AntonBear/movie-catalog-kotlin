package com.anton.movie_catalog_kotlin.repository

import com.anton.movie_catalog_kotlin.storage.TokenStorage
import com.anton.movie_catalog_kotlin.networking.MovieCatalogApi
import com.anton.movie_catalog_kotlin.networking.NetworkResult
import models.LoginRequest


interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): AuthResult
}

sealed class AuthResult {
    data object Success : AuthResult()
    data class Error(val message: String): AuthResult()
}

class AuthRepositoryImpl(
    private val movieCatalogApi: MovieCatalogApi,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): AuthResult {
        return when (val result = movieCatalogApi.login(loginRequest)) {
            is NetworkResult.Success -> {
                tokenStorage.saveApiKey(result.data.token)
                AuthResult.Success
            }

            is NetworkResult.Error -> {
                AuthResult.Error(result.error.message)
            }

            is NetworkResult.Exception -> {
                AuthResult.Error(result.e.message ?: "Unknown exception")
            }
        }
    }
}