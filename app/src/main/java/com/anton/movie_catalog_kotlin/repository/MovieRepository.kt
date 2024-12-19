package com.anton.movie_catalog_kotlin.repository

import android.util.Log
//import com.anton.movie_catalog_kotlin.models.ImageSource
import com.anton.movie_catalog_kotlin.models.MovieDetails
import com.anton.movie_catalog_kotlin.models.MovieElementModel
import com.anton.movie_catalog_kotlin.models.MoviesPagedListModel
import com.anton.movie_catalog_kotlin.networking.MovieCatalogApi
import retrofit2.Response


interface MovieRepository {
    suspend fun fetchMovies(page: Int): Result<MoviesPagedListModel>
    suspend fun getMovies(page: Int): List<MovieElementModel>
    suspend fun getRandomMoviePosterWithDetails(): MovieDetails
}

class MovieRepositoryImpl(private val movieCatalogApi: MovieCatalogApi) : MovieRepository {
    override suspend fun fetchMovies(page: Int): Result<MoviesPagedListModel> {
        return try {
            val response: Response<MoviesPagedListModel> = movieCatalogApi.getMovies(page)
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
            Result.failure(e)
        }
    }

    override suspend fun getMovies(page: Int): List<MovieElementModel> {
        return fetchMovies(page)
            .map {
                it.movies
            }
            .getOrElse {
                Log.e("MovieRepository", "Error fetching movies: $it")
                emptyList()
            }
    }

    override suspend fun getRandomMoviePosterWithDetails(): MovieDetails {
        val randomPage = (1..5).random()
        return fetchMovies(randomPage).fold(
            onSuccess = { movies ->

                val randomMovieIndex = movies.movies.indices.random()
                val randomMovie = movies.movies[randomMovieIndex]
                val genres = randomMovie.genres.map { it.name }.toList()
                // TODO: Remove redundant models
                val movieDetails = MovieDetails(
                    id = randomMovie.id,
                    name = randomMovie.name,
                    poster = randomMovie.poster,
                    year = randomMovie.year,
                    country = randomMovie.country,
                    genres = genres
                )
                return@fold movieDetails
            },
            onFailure = {
                Log.e("MovieRepository", "Error fetching movies: $it")
                return@fold MovieDetails(id = " ", name = " ", poster = "" , year = 0, country = "", genres = emptyList())
            }
        )
    }
}

