package com.anton.movie_catalog_kotlin.retrofit

import com.anton.movie_catalog_kotlin.models.FavoritesMoviesListModel
import com.anton.movie_catalog_kotlin.models.LoginResponse
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
    suspend fun deleteReview0(movieId: String): Result<Unit> {
        var nickName: String
        var reviewId: String

        // 1. Получаем профиль пользователя
        try {
            val response = api.getUserProfile()
            if (response.isSuccessful) {
                val body = response.body()
                nickName = body?.nickName ?: return Result.failure(Exception("NickName is null"))
            } else {
                return Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }

        // 2. Получаем детали фильма и находим review
        try {
            val movieDetailsResponse = api.getMovieDetail(movieId)
            if (movieDetailsResponse.isSuccessful) {
                val body = movieDetailsResponse.body()
                    ?: return Result.failure(Exception("Empty movie detail response body"))

                val review = body.reviews?.find { r -> r.author.nickName == nickName }
                    ?: return Result.failure(Exception("Review by $nickName not found"))

                reviewId = review.id // здесь уже безопасно, review не null
            } else {
                return Result.failure(Exception("Error: ${movieDetailsResponse.code()} ${movieDetailsResponse.message()}"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }

        // 3. Удаляем review
        try {
            val deleteReviewResponse = api.deleteReview(movieId, reviewId)
            if (deleteReviewResponse.isSuccessful) {
                return Result.success(Unit)
            } else {
                return Result.failure(Exception("Error: ${deleteReviewResponse.code()} ${deleteReviewResponse.message()}"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }


    suspend fun deleteReview1(movieId: String): Result<Unit> {
        var nickName: String = ""
        var reviewId: String = ""
        try {
            val response = api.getUserProfile()
            if (response.isSuccessful) {
                val body = response.body()
                if (body!=null) {
                    if (body.nickName != null) {
                        nickName = body.nickName
                    }              else {
                        return Result.failure(Exception("NickName is null"))
                    }
                }
                else {
                    return Result.failure(Exception("Empty response body"))
                }
            } else {
                return Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch(e: Exception) {
            return Result.failure(Exception(e))
        }

        try {
            val movieDetailsResponse = api.getMovieDetail(movieId)
            if (movieDetailsResponse.isSuccessful) {
                val body = movieDetailsResponse.body()
                    if(body != null) {
                        if (body.reviews != null) {
                            val review =  body.reviews.find { r -> r.author.nickName == nickName }
                            reviewId = review?.id.toString() // почему он просит проверку на null если я уже проверил body.reviews != null?

                        } else {
                            return Result.failure(Exception("review is empty"))
                        }
                    }
                else {
                    return Result.failure(Exception("Empty movie detail response body"))
                }
            } else {
                return Result.failure(Exception("Error: ${movieDetailsResponse.code()} ${movieDetailsResponse.message()}"))
            }

        }
        catch(e: Exception) {
                return Result.failure(Exception(e))
        }

        try {
            val deleteReviewResponse = api.deleteReview(movieId, reviewId)
            if (deleteReviewResponse.isSuccessful) {
                return Result.success(Unit)
            } else {
                return Result.failure(Exception("Error: ${deleteReviewResponse.code()} ${deleteReviewResponse.message()}"))
            }

        } catch(e: Exception) {
            return Result.failure(e)
        }

    }

    suspend fun loginUser(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = api.login(request)
                if(response.isSuccessful) {
                    val body = response.body()
                    if( body != null) {
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
