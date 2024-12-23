package com.anton.movie_catalog_kotlin.movieDetailsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.anton.movie_catalog_kotlin.models.FilmDetails
import com.anton.movie_catalog_kotlin.repository.KinopoiskRepository
import com.anton.movie_catalog_kotlin.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


sealed class ErrorType {
    data class NetworkError(val source: String, val message: String) : ErrorType()
    data object NotFoundError : ErrorType()
    data class ApiError(val code: Int?, val message: String) : ErrorType()
    data object NotAuthorizeError: ErrorType()
    data object UnknownError : ErrorType()
}


class MovieDetailsViewModel(
    private val movieId: String,
    private val movieRepository: MovieRepository,
    private val kinopoiskRepository: KinopoiskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState> = _uiState

    init {
        viewModelScope.launch {
            loadMovieDetails(movieId)
        }
    }

    private suspend fun loadMovieDetails(movieId: String) {
        try {
            val movieDetails = movieRepository.getMoviesDetails(movieId).getOrThrow()
            val kinopoiskDetails = movieDetails.name?.let { loadKinopoiskDetails(it) }
            _uiState.value = MovieDetailsUiState.Success(MovieDetailsCombined(movieDetails, kinopoiskDetails))
        } catch (e: Exception) {
            _uiState.value = when (e) {
                is SocketTimeoutException -> MovieDetailsUiState.Error(ErrorType.NetworkError("Kreosoft", "Connection timeout"))
                is IOException -> MovieDetailsUiState.Error(ErrorType.NetworkError("Kreosoft", e.message ?: "Network error"))
                is HttpException -> {
                    when (e.code()) {
                        401 -> MovieDetailsUiState.Error(ErrorType.NotAuthorizeError)
                        404 -> MovieDetailsUiState.Error(ErrorType.NotFoundError)
                        else -> MovieDetailsUiState.Error(ErrorType.ApiError(e.code(), e.message()))
                    }
                }
                else -> MovieDetailsUiState.Error(ErrorType.UnknownError)
            }
        }
    }


    private suspend fun loadKinopoiskDetails(keyword: String): FilmDetails? {
        return try {
            val kinopoiskMovies = kinopoiskRepository.fetchKinopoiskMoviesByKeyword(keyword).getOrThrow()
            kinopoiskMovies.films.firstOrNull()?.filmId?.let { kinopoiskId ->
                kinopoiskRepository.getFilmDetails(kinopoiskId).getOrThrow()
            }
        } catch (e: Exception) {
            Log.e("ViewModel", "Error loading Kinopoisk details: ${e.message}", e)
            null
        }
    }
}


class MovieDetailsViewModelFactory(
    private val movieId: String,
    private val movieRepository: MovieRepository,
    private val kinopoiskRepository: KinopoiskRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        @Suppress("UNCHECKED_CAST")
        return MovieDetailsViewModel(movieId, movieRepository, kinopoiskRepository) as T
    }
}
