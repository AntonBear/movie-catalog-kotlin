package com.anton.movie_catalog_kotlin.movieDetailsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anton.movie_catalog_kotlin.models.FilmDetails
import com.anton.movie_catalog_kotlin.models.ReviewModifyModel
import com.anton.movie_catalog_kotlin.repository.FavoriteMovieRepository
import com.anton.movie_catalog_kotlin.repository.KinopoiskRepository
import com.anton.movie_catalog_kotlin.repository.MovieRepository
import com.anton.movie_catalog_kotlin.repository.ProfileRepository
import com.anton.movie_catalog_kotlin.repository.ReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


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
    private val kinopoiskRepository: KinopoiskRepository,
    private val reviewRepository: ReviewRepository,
    private val favoriteMovieRepository: FavoriteMovieRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState> = _uiState


    private val _isAnonChecked = MutableStateFlow(false)
    val isAnonChecked = _isAnonChecked.asStateFlow()


    private val _directorPoster = MutableStateFlow("")
    val directorPoster: StateFlow<String> = _directorPoster

    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text
    fun updateText(newText: String) {
        _text.value = newText
        println(_text.value)
    }


    fun onAnonCheckedChange(isChecked: Boolean) {
        _isAnonChecked.value = isChecked
        println(_isAnonChecked.value)
    }
    private val _rating = MutableStateFlow(1)
    val rating: StateFlow<Int> = _rating

    fun updateRating(newRating: Int) {
        _rating.value = newRating
        println(_rating.value)
        println(movieId)
    }

    init {
        viewModelScope.launch {
            loadMovieDetails(movieId)
            isMovieFavorite()

        }
    }

    private val _isMovieFavorite = MutableStateFlow(false)
    val isMovieFavorite: StateFlow<Boolean> = _isMovieFavorite.asStateFlow()


    fun changeFavoriteMovieHandler() {
        viewModelScope.launch {
            try {
                when (_isMovieFavorite.value) {
                    true -> {
                        favoriteMovieRepository.deleteFavoriteMovies(movieId)
                        _isMovieFavorite.value = false
                    }
                    false -> {
                        favoriteMovieRepository.postFavoriteMovies(movieId)
                        _isMovieFavorite.value = true
                    }
                }
            } catch (e: Exception) {
                Log.e("MovieDetailsViewModel", "Error changing favorite status for movieId: $movieId", e)
            }
        }
    }


    private suspend fun getPersonItem(name: String) {
        viewModelScope.launch {
            try {
                val result = kinopoiskRepository.getPersonItem(name)
                result.onSuccess { personItem ->
                    personItem.posterUrl?.let { posterUrl ->
                        _directorPoster.value = posterUrl
                    } ?: run {
                        Log.w("GetPersonItem", "posterUrl is null for $name")
                        _directorPoster.value = ""
                    }
                }
            } catch (e: Exception) {
                Log.e("GetPersonItem", "Error getting person item: ${e.message}", e)
                _directorPoster.value = ""
            }
        }
    }


    private suspend fun isMovieFavorite() {
        viewModelScope.launch {
            try {
                val result = favoriteMovieRepository.getFavoriteMovieIds()
                result.onSuccess { favoriteMovieIds ->
                    _isMovieFavorite.value = favoriteMovieIds.contains(movieId)
                }
                    .onFailure {
                    Log.e("MovieDetailsViewModel", "Error loading favorite movies: ")
                        _isMovieFavorite.value = false

                }
            }
            catch (e:Exception) {
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
    }


    fun onSendReview() {
        viewModelScope.launch {
            try {
                val reviewText = _text.value
                val rating = _rating.value
                val isAnonymous = _isAnonChecked.value
                val postReviewBody = ReviewModifyModel(reviewText, rating, isAnonymous)
                reviewRepository.postReview(movieId, postReviewBody)
            }
            catch (e: Exception) {
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
    }


    private suspend fun loadMovieDetails(movieId: String) {
        try {
            val movieDetails = movieRepository.getMoviesDetails(movieId).getOrThrow()
            val kinopoiskDetails = movieDetails.name?.let { loadKinopoiskDetails(it) }
            getPersonItem(movieDetails.director ?: "")
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
    private val kinopoiskRepository: KinopoiskRepository,
    private val reviewRepository: ReviewRepository,
    private val favoriteMovieRepository: FavoriteMovieRepository,
    private val profileRepository: ProfileRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        @Suppress("UNCHECKED_CAST")
        return MovieDetailsViewModel(movieId, movieRepository, kinopoiskRepository, reviewRepository, favoriteMovieRepository, profileRepository) as T
    }
}
