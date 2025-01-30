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
    data class UnknownError(val message: String) : ErrorType()
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
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()

    fun changeFavoriteMovieHandler() {
        viewModelScope.launch {
            try {
                val currentUiState = _uiState.value
                val newIsFavorite = when (currentUiState) {
                    is MovieDetailsUiState.Success -> !currentUiState.isFavorite
                    else -> return@launch
                }

                val result = if (newIsFavorite) {
                    favoriteMovieRepository.postFavoriteMovies(movieId)
                } else {
                    favoriteMovieRepository.deleteFavoriteMovies(movieId)
                }

                result.onSuccess {
                    _uiState.value = (currentUiState as? MovieDetailsUiState.Success)?.copy(isFavorite = newIsFavorite)
                        ?: currentUiState
                }.onFailure {
                    _uiState.value = MovieDetailsUiState.Error(ErrorType.UnknownError(""))
                }
            } catch (e: Exception) {
                Log.e("MovieDetailsViewModel", "Error changing favorite status for movieId: $movieId", e)
                _uiState.value = MovieDetailsUiState.Error(ErrorType.UnknownError(e.message ?: ""))
            }
        }
    }

//    fun changeFavoriteMovieHandler() {
//        viewModelScope.launch {
//            try {
//                when (_isMovieFavorite.value) {
//                    true -> {
//                        favoriteMovieRepository.deleteFavoriteMovies(movieId)
//                        _isMovieFavorite.value = false
//                    }
//
//                    false -> {
//                        favoriteMovieRepository.postFavoriteMovies(movieId)
//                        _isMovieFavorite.value = true
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e(
//                    "MovieDetailsViewModel",
//                    "Error changing favorite status for movieId: $movieId",
//                    e
//                )
//            }
//        }
//    }

    init {
        viewModelScope.launch {
            loadMovieDetails(movieId)
        }
    }

    private val _directorPoster = MutableStateFlow("")
    val directorPoster: StateFlow<String> = _directorPoster.asStateFlow()

    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    private val _rating = MutableStateFlow(5f)
    val rating: StateFlow<Float> = _rating.asStateFlow()

    private val _isAnonChecked = MutableStateFlow(false)
    val isAnonChecked: StateFlow<Boolean> = _isAnonChecked.asStateFlow()

    fun updateText(newText: String) {
        _text.value = newText
    }

    fun updateRating(newRating: Float) {
        _rating.value = newRating
    }

    fun updateAnonChecked(isChecked: Boolean) {
        _isAnonChecked.value = isChecked
    }

    fun loadMovieDetails(movieId: String) = viewModelScope.launch {
        _uiState.value = MovieDetailsUiState.Loading
        try {
            val movieDetails = movieRepository.getMoviesDetails(movieId).getOrThrow()
            val kinopoiskDetails = movieDetails.name?.let { loadKinopoiskDetails(it) }
            getPersonItem(movieDetails.director ?: "")
            _uiState.value = MovieDetailsUiState.Success(
                MovieDetailsCombined(movieDetails, kinopoiskDetails),
                isFavorite = isMovieFavorite(movieId)
            )
        } catch (e: Exception) {
            _uiState.value = handleError(e)
        }
    }

    private suspend fun isMovieFavorite(movieId:String) : Boolean {
        return try {
            favoriteMovieRepository.getFavoriteMovieIds().getOrThrow().contains(movieId)
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun getPersonItem(name: String) {
        try {
            val result = kinopoiskRepository.getPersonItem(name).getOrThrow()
            _directorPoster.value = result.posterUrl ?: ""
        } catch (e: Exception) {
            Log.e("GetPersonItem", "Error getting person item: ${e.message}", e)
            _directorPoster.value = ""
        }
    }

    fun onSendReview() = viewModelScope.launch {
        try {
            val reviewText = _text.value
            val rating = _rating.value.toInt()
            val isAnonymous = _isAnonChecked.value
            val postReviewBody = ReviewModifyModel(reviewText, rating, isAnonymous)
            reviewRepository.postReview(movieId, postReviewBody)
        } catch (e: Exception) {
            _uiState.value = handleError(e)
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

    private fun handleError(e: Exception): MovieDetailsUiState.Error = when (e) {
        is SocketTimeoutException, is IOException -> MovieDetailsUiState.Error(ErrorType.NetworkError("Kreosoft", "Connection timeout or Network error"))
        is HttpException -> when (e.code()) {
            401 -> MovieDetailsUiState.Error(ErrorType.NotAuthorizeError)
            404 -> MovieDetailsUiState.Error(ErrorType.NotFoundError)
            else -> MovieDetailsUiState.Error(ErrorType.ApiError(e.code(), e.message()))
        }
        else -> MovieDetailsUiState.Error(ErrorType.UnknownError(e.message ?: "Unknown error"))
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
