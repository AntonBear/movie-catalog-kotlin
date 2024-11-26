package com.anton.movie_catalog_kotlin.repository

import com.anton.movie_catalog_kotlin.models.SignUpRequest
import com.anton.movie_catalog_kotlin.networking.MovieCatalogApi
import com.anton.movie_catalog_kotlin.networking.NetworkResult
import org.json.JSONObject


interface SignUpRepository {
    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResult
}

sealed class SignUpResult {
    data object Success : SignUpResult()
    data class Error(val message: String) : SignUpResult()
}

class SignUpRepositoryImpl(private val movieCatalogApi: MovieCatalogApi) : SignUpRepository {

    override suspend fun signUp(signUpRequest: SignUpRequest): SignUpResult {
        return try {
            val response = movieCatalogApi.signUp(signUpRequest)
            if (response.isSuccessful) {
                SignUpResult.Success
            } else {
                SignUpResult.Error("Unknown error")
            }
        } catch (e: Exception) {
            SignUpResult.Error(e.message ?: "Network error")
        }
    }
}