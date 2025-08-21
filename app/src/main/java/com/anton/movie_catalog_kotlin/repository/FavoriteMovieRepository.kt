package com.anton.movie_catalog_kotlin.repository

import android.util.Log
import com.anton.movie_catalog_kotlin.models.MovieDetails
import com.anton.movie_catalog_kotlin.models.MovieListModel
import com.anton.movie_catalog_kotlin.networking.MovieCatalogApi
import retrofit2.HttpException
import retrofit2.Response

interface FavoriteMovieRepository {
    suspend fun getFavoriteMovies() : Result<MovieListModel>
    suspend fun postFavoriteMovies(movieId: String) : Result<Unit>
    suspend fun deleteFavoriteMovies(movieId: String) : Result<Unit>
    suspend fun getFavoriteMovieIds(): Result<Set<String>>
}

class FavoriteMovieRepositoryImpl(private val movieCatalogApi: MovieCatalogApi): FavoriteMovieRepository {

    override suspend fun getFavoriteMovieIds(): Result<Set<String>> {
        return try {
            val result = getFavoriteMovies()
            val ids = result.getOrNull()?.movies?.map { it.id }?.toSet() ?: emptySet()
            Result.success(ids)
        } catch (e: Exception) {
            Log.e("FavoriteMovieRepository", "Error fetching favorite movie IDs: ${e.message}", e)
            Result.failure(e)
        }
    }



    override suspend fun getFavoriteMovies(): Result<MovieListModel> {
        return try {
            val response: Response<MovieListModel> = movieCatalogApi.getFavoriteMovies()
            when {
                response.isSuccessful -> {
                    val movies = response.body()
                    if (movies != null) {
                        Result.success(movies)
                    } else {
                        Result.failure(Exception("Null response body"))
                    }
                }
                else -> Result.failure(Exception("HTTP error ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("postReview", e.message.toString())
            Result.failure(e)
        }
    }

    override suspend fun postFavoriteMovies(movieId: String) : Result<Unit> {
        return try {
         val response = movieCatalogApi.postFavoriteMovies(movieId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(HttpException(response)) }
        }
        catch(e: Exception) {
            Log.e("postFavoriteMovies", e.message.toString())
            Result.failure(e)
        }
    }

    override suspend fun deleteFavoriteMovies(movieId: String) : Result<Unit> {
        return try {
            val response = movieCatalogApi.deleteFavoriteMovies(movieId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(HttpException(response))
            }
        }
        catch (e:Exception) {
            Log.e("deleteFavoriteMovies", e.message.toString())
            Result.failure(e)
        }
    }
}