package com.anton.movie_catalog_kotlin

import com.anton.movie_catalog_kotlin.retrofit.KreosoftApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


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