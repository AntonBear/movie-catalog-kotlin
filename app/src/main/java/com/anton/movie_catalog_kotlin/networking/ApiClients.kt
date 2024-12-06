package com.anton.movie_catalog_kotlin.networking

import android.content.Context
import com.anton.movie_catalog_kotlin.MovieCatalogApplication
import com.anton.movie_catalog_kotlin.storage.SecureTokenStorage
import com.anton.movie_catalog_kotlin.storage.TokenStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object ApiClients {
    private val applicationContext: Context
        get() = MovieCatalogApplication.applicationContext

    private val tokenStorage: TokenStorage by lazy {
        SecureTokenStorage(applicationContext)
    }

    val kinopoiskOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val apiKey = tokenStorage.getApiKey()
                val headerValue = apiKey ?: "default_api_key"
                val original = chain.request()
                val request = original.newBuilder()
                    .header("X-API-KEY", headerValue)
                    .build()
                chain.proceed(request)
            }
            .build()

    }

    val movieCatalogOkHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    }
}
