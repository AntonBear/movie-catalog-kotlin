package com.anton.movie_catalog_kotlin

import com.anton.movie_catalog_kotlin.models_old.SignUpRequest
import com.anton.movie_catalog_kotlin.retrofit.KreosoftRepository
import kotlinx.coroutines.runBlocking
import models.LoginRequest
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.UUID

class ApiTest {

//    @Test
////    fun getListMovieDetails() = runBlocking {
////        val response = KreosoftRepository
////    }

    @Test
    fun testRegUserRequest() = runBlocking {
        val randomUserName = "User_" + UUID.randomUUID().toString().take(8)

        val response = KreosoftRetrofitClient.api.register(
            SignUpRequest(
                userName = randomUserName,
                name = "Anton",
                password = "greedisgood",
                email = "holzed15@gmail.com",
                birthDate = "2024-11-06T10:26:03.128Z",
                gender = 1,
            )
        )
        println("register response: ${response.body()?.token}")

        assertNotNull(response.body()?.token, "Токен не должен быть null")
    }

    @Test
    fun test_login_request() = runBlocking {
        val response = KreosoftRetrofitClient.api.login(
            LoginRequest(
                username = "Anton",
                password = "greedisgood"
            )
        )
        println("Ответ сервера: $response")

        assertNotNull(response.body()?.token, "Токен не должен быть null")
    }

    @Test
    fun test_get_movie_detail() = runBlocking {
        val response = KreosoftRetrofitClient.api.getMovieDetail("b6c5228b-91fb-43a1-a2ac-08d9b9f3d2a2")
        println("Ответ сервера: ${response.body().toString()}")
    }

}