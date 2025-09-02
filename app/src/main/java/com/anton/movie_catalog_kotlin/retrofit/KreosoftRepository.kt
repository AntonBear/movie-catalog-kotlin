package com.anton.movie_catalog_kotlin.retrofit

import com.anton.movie_catalog_kotlin.models.FavoritesMoviesListModel
import com.anton.movie_catalog_kotlin.models.LoginResponse
import com.anton.movie_catalog_kotlin.models.MovieDetailsModel
import com.anton.movie_catalog_kotlin.models.SignUpRequest
import com.anton.movie_catalog_kotlin.models.SignUpResponse
import com.anton.movie_catalog_kotlin.storage.TokenStorage
import models.LoginRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KreosoftRepository @Inject constructor(
    private val api: KreosoftApi,
    private val tokenStorage: TokenStorage
) {

    suspend fun regUser(request: SignUpRequest): Result<String> {
        return try {
            val response = api.register(request)
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            } else {
                val body = response.body()
                val token = body?.token
                if (token != null) {
                    Result.success(token)
                } else {
                    return Result.failure(Exception("Response body is null"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun deleteReview(movieId: String): Result<Unit> {
        return try {
            // 1. Получаем профиль
            val profileResponse = api.getUserProfile()
            if (!profileResponse.isSuccessful) {
                return Result.failure(Exception("Error: ${profileResponse.code()} ${profileResponse.message()}"))
            }
            val nickName = profileResponse.body()?.nickName
                ?: return Result.failure(Exception("NickName is null"))

            // 2. Получаем детали фильма
            val movieResponse = api.getMovieDetail(movieId)
            if (!movieResponse.isSuccessful) {
                return Result.failure(Exception("Error: ${movieResponse.code()} ${movieResponse.message()}"))
            }
            val review = movieResponse.body()?.reviews
                ?.find { r -> r.author.nickName == nickName }
                ?: return Result.failure(Exception("Review by $nickName not found"))

            // 3. Удаляем отзыв
            val deleteResponse = api.deleteReview(movieId, review.id)
            if (deleteResponse.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error: ${deleteResponse.code()} ${deleteResponse.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun loginUser(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = api.login(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
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
