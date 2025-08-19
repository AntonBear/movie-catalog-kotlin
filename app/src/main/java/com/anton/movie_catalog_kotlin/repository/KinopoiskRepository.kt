package com.anton.movie_catalog_kotlin.repository

import com.anton.movie_catalog_kotlin.models.FilmDetails
import com.anton.movie_catalog_kotlin.models.MovieSearchModel
import com.anton.movie_catalog_kotlin.models.PersonItem
import com.anton.movie_catalog_kotlin.models.PersonListModel
import com.anton.movie_catalog_kotlin.networking.KinopoiskApi
import retrofit2.Response

interface FriendsRepository {
    fun deleteFriend(id: String)
    fun createFriend()
}


class FriendsRepositoryImpl() : FriendsRepository {
    override fun deleteFriend(id: String) {
        TODO("Not yet implemented")
    }

    override fun createFriend() {
        TODO("Not yet implemented")
    }

}




interface KinopoiskRepository {
    suspend fun fetchKinopoiskMoviesByKeyword(keyword: String) : Result<MovieSearchModel>
    suspend fun getFilmDetails(id: Int): Result<FilmDetails>
    suspend fun getPersonItem(name: String): Result<PersonItem>
}

class KinopoiskRepositoryImpl(private val kinopoiskApi: KinopoiskApi): KinopoiskRepository {

    override suspend fun getPersonItem(name: String): Result<PersonItem> {
        return try {
            val response = kinopoiskApi.getPersonList(name)
            if (response.isSuccessful) {
                response.body()?.items?.firstOrNull()?.let { personItem ->
                    Result.success(personItem)
                } ?: Result.failure(Exception("No person found for name: $name"))
            } else {
                Result.failure(Exception("HTTP error ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.message} for name: $name"))
        }
    }


    override suspend fun fetchKinopoiskMoviesByKeyword(keyword: String): Result<MovieSearchModel> {
        return try {
            val response: Response<MovieSearchModel> = kinopoiskApi.searchMovies(keyword)
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

    override suspend fun getFilmDetails(id: Int): Result<FilmDetails> {
        return try {
            val response: Response<FilmDetails> = kinopoiskApi.getFilmDetails(id)
            when {
                response.isSuccessful -> {
                    val moviesDetails = response.body()
                    if(moviesDetails != null) {
                        Result.success(moviesDetails)
                    }
                    else {
                        Result.failure(Exception("Null response body"))
                    }
                }
                else -> Result.failure(Exception("HTTP error ${response.code()}"))
            }
        }
        catch (e:Exception) {
            Result.failure(e)
        }
    }
}