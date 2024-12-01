package com.anton.movie_catalog_kotlin.repository

import com.anton.movie_catalog_kotlin.models.SignUpRequest
import com.anton.movie_catalog_kotlin.networking.MovieCatalogApi
import com.anton.movie_catalog_kotlin.storage.TokenStorage
import models.LoginRequest
import retrofit2.Response

interface UserAuthRepository {
    suspend fun signUp(signUpRequest: SignUpRequest)
    suspend fun signIn(loginRequest: LoginRequest)
}

class UserAuthRepositoryImpl(
    private val movieCatalogApi: MovieCatalogApi,
    private val tokenStorage: TokenStorage
) : UserAuthRepository {

    override suspend fun signUp(signUpRequest: SignUpRequest) {
        val response = movieCatalogApi.signUp(signUpRequest)
        handleResponse(response, "SignUp") { it.token }
    }

    override suspend fun signIn(loginRequest: LoginRequest) {
        val response = movieCatalogApi.login(loginRequest)
        handleResponse(response, "Login") { it.token }
    }

    private fun <T> handleResponse(
        response: Response<T>,
        operation: String,
        tokenExtractor: (T) -> String
    ) {
        if (response.isSuccessful) {
            response.body()?.let {
                tokenStorage.saveApiKey(tokenExtractor(it))
            } ?: throw Exception("$operation successful, but response body is null")
        } else {
            throw Exception("$operation failed: ${response.code()}")
        }
    }
}