package com.anton.movie_catalog_kotlin.signin

import TokenStorage
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.movie_catalog_kotlin.networking.MovieCatalogApi
import com.anton.movie_catalog_kotlin.networking.NetworkResult
import com.anton.movie_catalog_kotlin.services.ServiceLocator
import kotlinx.coroutines.launch
import models.LoginRequest

class SignInViewModel(
    private val movieCatalogApi: MovieCatalogApi = ServiceLocator.movieCatalogApi,
    private val tokenStorage: TokenStorage = ServiceLocator.tokenStorage
): ViewModel() {
    fun login(username: String, password: String) {
        viewModelScope.launch {
            val loginRequest = LoginRequest(username, password)
            val result = movieCatalogApi.login(loginRequest)
            when (result) {
                is NetworkResult.Success -> {
                    tokenStorage.apiAuthToken = result.data.token
                    println(tokenStorage.apiAuthToken)
                }
                is NetworkResult.Error -> {
                    println("error: ${result.error.message}")
                }
                is NetworkResult.Exception -> {
                    println(result.e)
                }
            }
        }
    }
}