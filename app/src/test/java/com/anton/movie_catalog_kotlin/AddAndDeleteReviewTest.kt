package com.anton.movie_catalog_kotlin

import com.anton.movie_catalog_kotlin.models.*
import com.anton.movie_catalog_kotlin.retrofit.KreosoftApi
import com.anton.movie_catalog_kotlin.retrofit.KreosoftRetrofitClient
import kotlinx.coroutines.runBlocking
import models.LoginRequest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AddAndDeleteReviewTest {

    @Test
    fun addAndDeleteReviewTest() = runBlocking {
        val api = KreosoftRetrofitClient.createApi(
            KreosoftRetrofitClient.api.login(LoginRequest("Anton", "greedisgood")).token
        )

        // Получаем фильм для теста
        val movieId = api.getMoviesPage(1).body()!!.movies.first().id

        // Создаем отзыв
        val review = ReviewShortModel(
            reviewText = "Тестовый отзыв",
            rating = 5,
            isAnonymous = false
        )
        val addResponse = api.addReview(movieId, review)
        assertTrue(addResponse.isSuccessful)

        // Проверяем отзыв
        val myReview = api.getMovieDetail(movieId).body()?.reviews?.find { it.author.nickName == "Anton" }
        assertNotNull(myReview)
        val reviewId = myReview!!.id

        // Удаляем отзыв
        val deleteResponse = api.deleteReview(movieId, reviewId)
        assertTrue(deleteResponse.isSuccessful)

        // Проверяем, что отзыва больше нет
        val afterDelete = api.getMovieDetail(movieId).body()?.reviews?.find { it.id == reviewId }
        assertNull(afterDelete)
    }
}