package com.anton.movie_catalog_kotlin

import com.anton.movie_catalog_kotlin.retrofit.KinopoiskRetrofitClient
import com.anton.movie_catalog_kotlin.retrofit.KreosoftRetrofitClient
import kotlinx.coroutines.runBlocking
import models.LoginRequest
import org.junit.Assert.assertNotNull
import org.junit.Test

class ApiTest {

    @Test
    fun `test login request`() = runBlocking {
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
    fun `get movie by keyword`() = runBlocking {
        val response = KinopoiskRetrofitClient.api.searchByKeyword(
            apiKey = "5673684a-da0e-43e0-bfc9-4829489bbe4f",
            keyword = "Семнадцать",
        )
        println("Ответ сервера $response")

        assertNotNull(response.keyword, "Не должен быть null")

    }
}