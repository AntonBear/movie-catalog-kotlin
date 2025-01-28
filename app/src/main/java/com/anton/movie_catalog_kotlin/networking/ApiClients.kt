package com.anton.movie_catalog_kotlin.networking

import android.content.Context
import com.anton.movie_catalog_kotlin.MovieCatalogApplication
import com.anton.movie_catalog_kotlin.storage.SecureTokenStorage
import com.anton.movie_catalog_kotlin.storage.TokenStorage
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


object ApiClients {
    private val applicationContext: Context
        get() = MovieCatalogApplication.applicationContext

    private val tokenStorage: TokenStorage by lazy {
        SecureTokenStorage(applicationContext)
    }

    val kinopoiskOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("X-API-KEY", "5673684a-da0e-43e0-bfc9-4829489bbe4f")
                    .build()
                chain.proceed(request)
            }
            .build()
    }


    val movieCatalogOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .authenticator(object : Authenticator {
                override fun authenticate(route: Route?, response: Response): Request? {
                    val token = tokenStorage.getToken() ?: return null

                    return response.request.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                }
            })
            .build()
    }







}
