package com.anton.movie_catalog_kotlin.signup

data class SignUpUiState(
    val isLoading: Boolean = false,
    val error: SignUpError? = null,
    val isUserLoggedIn: Boolean = false,
    val isSignUpButtonEnabled: Boolean = false,
    val isFemaleSelected: Boolean = false,
    val isMaleSelected: Boolean = false,
)