package com.anton.movie_catalog_kotlin.retrofit

import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient

object KreosoftRetrofitClient {

    private const val BASE_URL = "https://react-midterm.kreosoft.space/"


    val json = Json { ignoreUnknownKeys = true }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IkFudG9uIiwiZW1haWwiOiJvb29vb29vb0BleGFtcGxlLmNvbSIsIm5iZiI6MTc1NjA2NDk4OSwiZXhwIjoxNzU2MDY4NTg5LCJpYXQiOjE3NTYwNjQ5ODksImlzcyI6Imh0dHBzOi8vcmVhY3QtbWlkdGVybS5rcmVvc29mdC5zcGFjZS8iLCJhdWQiOiJodHRwczovL3JlYWN0LW1pZHRlcm0ua3Jlb3NvZnQuc3BhY2UvIn0.neIZ5YWl4GToV8GwB1BJoGVpexw4yL8GreKvFSYqtsA")
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val api: KreosoftApi = retrofit.create(KreosoftApi::class.java)



    fun createApi(token: String): KreosoftApi {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
            .create(KreosoftApi::class.java)
    }


}