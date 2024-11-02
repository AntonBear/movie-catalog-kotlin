package com.anton.movie_catalog_kotlin.services

import SecureTokenStorage
import TokenStorage
import android.content.Context
import com.anton.movie_catalog_kotlin.networking.KinopoiskApi
import com.anton.movie_catalog_kotlin.networking.MovieCatalogApi
import com.anton.movie_catalog_kotlin.MovieCatalogApplication
import com.anton.movie_catalog_kotlin.networking.NetworkResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ServiceLocator {
    val kinopoiskApi: KinopoiskApi by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("X-API-KEY", "5673684a-da0e-43e0-bfc9-4829489bbe4f")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech/")
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json".toMediaType()))
            .client(client)
            .build()

        retrofit.create(KinopoiskApi::class.java)
    }

    val movieCatalogApi: MovieCatalogApi by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://react-midterm.kreosoft.space/")
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json".toMediaType()))
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .client(client)
            .build()

        retrofit.create(MovieCatalogApi::class.java)
    }

    val applicationContext: Context
        get() = MovieCatalogApplication.applicationContext

    val tokenStorage: TokenStorage by lazy {
        SecureTokenStorage(applicationContext)
    }
}