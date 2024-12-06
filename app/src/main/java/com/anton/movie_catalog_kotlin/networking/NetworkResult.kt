package com.anton.movie_catalog_kotlin.networking

sealed interface NetworkResult<Success : Any, Error: Any> {
    data class Success<Success : Any, Error: Any>(val data: Success) : NetworkResult<Success, Error>
    data class Error<Success : Any, Error: Any>(val error: Error) : NetworkResult<Success, Error>
    data class Exception<Success : Any, Error: Any>(val e: Throwable) : NetworkResult<Success, Error>
}
