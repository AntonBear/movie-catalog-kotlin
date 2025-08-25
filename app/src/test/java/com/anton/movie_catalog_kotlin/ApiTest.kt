package com.anton.movie_catalog_kotlin

import com.anton.movie_catalog_kotlin.retrofit.KinopoiskRetrofitClient
import com.anton.movie_catalog_kotlin.retrofit.KreosoftRetrofitClient
import kotlinx.coroutines.runBlocking
import models.LoginRequest
import org.junit.Assert.assertNotNull
import org.junit.Test

class ApiTest {


    @Test
    fun test_login_request() = runBlocking {
        val response = KreosoftRetrofitClient.api.login(
            LoginRequest(
                username = "Anton",
                password = "greedisgood"
            )
        )

        println("Ответ сервера: $response")

        // Проверяем что токен вернулся (или хотя бы не null)
        assertNotNull(response.token, "Токен не должен быть null")
    }

    @Test
    fun test_get_movie_detail() = runBlocking {
        val response = KreosoftRetrofitClient.api.getMovieDetail("b6c5228b-91fb-43a1-a2ac-08d9b9f3d2a2")


        println("Ответ сервера: ${response.body().toString()}")

    }




    @Test
    fun get_movie_by_keyword() = runBlocking {
        val response = KinopoiskRetrofitClient.api.searchByKeyword(
            apiKey = "5673684a-da0e-43e0-bfc9-4829489bbe4f",
            keyword = "Семнадцать",
        )
        println("Ответ сервера $response")

        assertNotNull(response.keyword, "Не должен быть null")

    }
}