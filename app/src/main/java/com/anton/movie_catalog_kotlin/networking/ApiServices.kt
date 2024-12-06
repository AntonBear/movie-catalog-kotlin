package com.anton.movie_catalog_kotlin.networking

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ApiServices {

    val kinopoiskApi: KinopoiskApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech/")
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json".toMediaType()))
            .client(ApiClients.kinopoiskOkHttpClient)
            .build()
            .create(KinopoiskApi::class.java)
    }

    val movieCatalogApi: MovieCatalogApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://react-midterm.kreosoft.space/")
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json".toMediaType()))
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .client(ApiClients.movieCatalogOkHttpClient)
            .build()
            .create(MovieCatalogApi::class.java)
    }
}

