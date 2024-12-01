package com.anton.movie_catalog_kotlin.signup

sealed interface SignUpError {
    data object InvalidFields: SignUpError
    data class RequestError(val message: String?): SignUpError
}