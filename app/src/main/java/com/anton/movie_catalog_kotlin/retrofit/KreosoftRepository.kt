package com.anton.movie_catalog_kotlin.retrofit

import com.anton.movie_catalog_kotlin.models.FavoritesMoviesListModel
import com.anton.movie_catalog_kotlin.models.MovieDetailsModel
import com.anton.movie_catalog_kotlin.storage.TokenStorage
import models.LoginRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KreosoftRepository @Inject constructor(
    private val api: KreosoftApi,
    private val tokenStorage: TokenStorage
) {

    suspend fun loginUser(request: LoginRequest): Boolean {
        return try {
            val response = api.login(request)
            if (response.isSuccessful) {
                val body = response.body()
                val token = body?.token
                if (!token.isNullOrBlank()) {
                    tokenStorage.saveToken(token)
                    return true
                }
            }
            false
        } catch (e: Exception) {
            false
        }
    }

    suspend fun logout() {
        api.logout()
        tokenStorage.deleteToken()
    }

    suspend fun getMovieDetail(movieId: String): Result<MovieDetailsModel> {
        return try {
            val response = api.getMovieDetail(movieId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFavorites(): FavoritesMoviesListModel? {
        return try {
            val response = api.getFavoritesMovies()
            if (response.isSuccessful) {
                response.body() // возвращаем тело ответа
            } else {
                null // если сервер вернул ошибку
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null // если произошла ошибка сети или что-то еще
        }
    }

    suspend fun addFavorite(movieId: String) {
        api.addFavoritesMovie(movieId)
    }

    suspend fun deleteFavorite(movieId: String) {
        api.deleteFavoriteMovie(movieId)
    }
}
