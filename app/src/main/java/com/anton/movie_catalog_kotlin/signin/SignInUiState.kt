package com.anton.movie_catalog_kotlin.signin

data class SignInUiState(
    val isLoading: Boolean = false,
    val error: SignInError? = null,
    val isUserLoggedIn: Boolean = false,
    val isAuthButtonEnabled: Boolean = false,
)