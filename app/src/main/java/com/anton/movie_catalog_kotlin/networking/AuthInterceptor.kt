package com.anton.movie_catalog_kotlin.networking

import com.anton.movie_catalog_kotlin.storage.TokenStorage
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenStorage: TokenStorage) : Interceptor {

    private val _unauthorizedError = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val unauthorizedError = _unauthorizedError.asSharedFlow()

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val response = chain.proceed(original)

        if (response.code == 401) {
            tokenStorage.deleteToken()
            _unauthorizedError.tryEmit(Unit)
        }

        return response
    }
}
