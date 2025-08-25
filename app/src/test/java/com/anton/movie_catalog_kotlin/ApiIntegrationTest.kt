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

class ApiIntegrationTest {

    private lateinit var token: String
    private lateinit var testMovieId: String
    private var createdReviewId: String? = null

    private fun apiWithToken(): KreosoftApi = KreosoftRetrofitClient.createApi(token)

    // --- Общий setup ---
    @Before
    fun setup() = runBlocking {
        // Логинимся один раз перед тестами
        if (!this@ApiIntegrationTest::token.isInitialized) {
            val response = KreosoftRetrofitClient.api.login(
                LoginRequest("Anton", "greedisgood")
            )
            token = response.token
            assertNotNull("Токен не должен быть null", token)
        }

        // Получаем тестовый фильм один раз перед тестами
        if (!this@ApiIntegrationTest::testMovieId.isInitialized) {
            val api = apiWithToken()
            val moviesResponse = api.getMoviesPage(1)
            assertTrue("Не удалось получить страницу фильмов", moviesResponse.isSuccessful)
            val movies = moviesResponse.body()?.movies
            assertNotNull("Список фильмов пустой", movies)
            testMovieId = movies!!.first().id
        }
    }

    // --- Auth ---

    @Test
    fun loginTest() = runBlocking {
        val response = KreosoftRetrofitClient.api.login(LoginRequest("Anton", "greedisgood"))
        println("Login response: $response")
        assertNotNull("Токен не должен быть null", response.token)
    }

    @Test
    fun registerTest() = runBlocking {
        val username = "TestUser${System.currentTimeMillis()}"
        val response = KreosoftRetrofitClient.api.register(
            SignUpRequest(
                userName = username,
                password = "password123",
                email = "antonbear@gmail.com",
                birthDate = "10.11.1997",
                gender = Gender.MALE,
                name = "Anton"
            )
        )
        println("Register response: $response")
        assertNotNull("Регистрация должна вернуть объект", response)
    }

    @Test
    fun logoutTest() = runBlocking {
        val api = apiWithToken()
        val response = api.logout()
        println("Logout result: $response")
        assertNotNull(response)
    }

    // --- User ---

    @Test
    fun getUserProfileTest() = runBlocking {
        val api = apiWithToken()
        val response: Response<ProfileModel> = api.getUserProfile()
        println("Profile: ${response.body()}")
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }

    @Test
    fun editUserProfileTest() = runBlocking {
        val api = apiWithToken()
        val currentProfile = api.getUserProfile().body()
        assertNotNull(currentProfile)

        val updatedProfile = currentProfile!!.copy(nickName = currentProfile.nickName + "_edit")
        val response: Response<ProfileModel> = api.editUserProfile(updatedProfile)
        println("Edit profile response: $response")
        assertTrue(response.isSuccessful)
        assertEquals(updatedProfile.nickName, response.body()?.nickName)
    }

    // --- Movies ---

    @Test
    fun getMoviesPageTest() = runBlocking {
        val api = apiWithToken()
        val response = api.getMoviesPage(1)
        println("Movies page: ${response.body()}")
        assertTrue(response.isSuccessful)
        assertNotNull(response.body()?.movies)
        assertTrue(response.body()!!.movies.isNotEmpty())
    }

    @Test
    fun getMovieDetailTest() = runBlocking {
        val api = apiWithToken()
        val response = api.getMovieDetail(testMovieId)
        println("Movie detail: ${response.body()}")
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }

    // --- Favorites ---

    @Test
    fun addToFavoritesTest() = runBlocking {
        val api = apiWithToken()
        val response = api.addFavoritesMovie(testMovieId)
        println("Add to favorites: $response")
        assertTrue(response.isSuccessful)
    }

    @Test
    fun deleteFromFavoritesTest() = runBlocking {
        val api = apiWithToken()
        val response = api.deleteFavoriteMovie(testMovieId)
        println("Delete from favorites: $response")
        assertTrue(response.isSuccessful)
    }

    @Test
    fun getFavoritesMoviesTest() = runBlocking {
        val api = apiWithToken()
        val response = api.getFavoritesMovies()
        println("Favorites movies: $response")
        assertNotNull(response.movies)
    }

    // --- Reviews ---

    @Test
    fun addReviewTest() = runBlocking {
        val api = apiWithToken()
        val review = ReviewShortModel(
            reviewText = "Тестовый отзыв",
            rating = 5,
            isAnonymous = false
        )
        val response = api.addReview(testMovieId, review)
        println("Add review response: $response")
        assertTrue(response.isSuccessful)

        val movieDetails = api.getMovieDetail(testMovieId)
        val myReview = movieDetails.body()?.reviews?.find { it.author.nickName == "Anton" }
        assertNotNull(myReview)
        createdReviewId = myReview?.id
    }

    @Test
    fun editReviewTest() = runBlocking {
        val api = apiWithToken()
        assertNotNull("Сначала нужно создать отзыв", createdReviewId)
        val updatedReview = ReviewShortModel(
            reviewText = "Обновлённый отзыв",
            rating = 4,
            isAnonymous = false
        )
        val response = api.editReview(testMovieId, createdReviewId!!, updatedReview)
        println("Edit review response: $response")
        assertTrue(response.isSuccessful)
    }

    @Test
    fun deleteReviewTest() = runBlocking {
        val api = apiWithToken()
        assertNotNull("Сначала нужно создать отзыв", createdReviewId)
        val response = api.deleteReview(testMovieId, createdReviewId!!)
        println("Delete review response: $response")
        assertTrue(response.isSuccessful)
    }
}
