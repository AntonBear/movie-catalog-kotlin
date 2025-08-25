package com.anton.movie_catalog_kotlin.retrofit

import com.anton.movie_catalog_kotlin.storage.TokenStorage
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val tokenStorage: TokenStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val token = tokenStorage.getToken()
        if (!token.isNullOrBlank()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}
