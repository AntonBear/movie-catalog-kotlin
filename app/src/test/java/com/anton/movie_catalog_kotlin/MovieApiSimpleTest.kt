package com.anton.movie_catalog_kotlin

import com.anton.movie_catalog_kotlin.models.*
import com.anton.movie_catalog_kotlin.retrofit.KreosoftApi
import com.anton.movie_catalog_kotlin.KreosoftRetrofitClient
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import models.LoginRequest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class MovieApiSimpleTest {

    private val api: KreosoftApi = Retrofit.Builder()
        .baseUrl("https://react-midterm.kreosoft.space/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(KreosoftApi::class.java)

    @Test
    fun getMovieDetail() = runBlocking {
        val response = api.getMovieDetailRaw("b6c5228b-91fb-43a1-a2ac-08d9b9f3d2a2")

        if (response.isSuccessful) {
            val rawJson = response.body()?.string()
            println("Server response JSON: $rawJson")
        } else {
            println("Error response: ${response.code()} - ${response.errorBody()?.string()}")
        }
    }
}