package com.anton.movie_catalog_kotlin

import com.anton.movie_catalog_kotlin.retrofit.KreosoftApi
import com.anton.movie_catalog_kotlin.storage.TokenStorage
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object KreosoftRetrofitClient {

    private const val BASE_URL = "https://react-midterm.kreosoft.space/"

    val json = Json { ignoreUnknownKeys = true }

    lateinit var tokenStorage: TokenStorage

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = tokenStorage.getToken()
                val requestBuilder = chain.request().newBuilder()
                if (!token.isNullOrBlank()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val api: KreosoftApi = retrofit.create(KreosoftApi::class.java)

}