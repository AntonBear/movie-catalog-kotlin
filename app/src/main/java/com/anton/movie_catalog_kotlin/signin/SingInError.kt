package com.anton.movie_catalog_kotlin.signin

sealed interface SignInError {
    data object InvalidFields: SignInError
    data class RequestError(val message: String?): SignInError
}