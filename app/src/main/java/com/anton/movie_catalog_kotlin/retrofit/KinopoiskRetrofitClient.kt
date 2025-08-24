package com.anton.movie_catalog_kotlin.retrofit

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object KinopoiskRetrofitClient {
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val api: KinopoiskApi =  retrofit.create(KinopoiskApi::class.java)
}