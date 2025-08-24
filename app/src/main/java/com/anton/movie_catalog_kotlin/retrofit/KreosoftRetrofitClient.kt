package com.anton.movie_catalog_kotlin.retrofit

import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

object KreosoftRetrofitClient {
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://react-midterm.kreosoft.space/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val api: KreosoftApi = retrofit.create(KreosoftApi::class.java)
}