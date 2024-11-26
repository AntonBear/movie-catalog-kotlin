package com.anton.movie_catalog_kotlin.feedScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anton.movie_catalog_kotlin.repository.MovieRepository
import com.anton.movie_catalog_kotlin.models.ImageSource
import com.anton.movie_catalog_kotlin.models.MovieDetails
import com.anton.movie_catalog_kotlin.repository.Repositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class FeedScreenViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                FeedScreenViewModel(movieRepository = Repositories.movieRepository)
            }
        }
    }

    private val _movieDetails =
        MutableStateFlow(MovieDetails(id = "", name = "Loading", poster = ImageSource.Remote(url = ""), year = 2024, country = "loading", genres = listOf("loading")))
    //when data is loading form server
    val movieDetails: StateFlow<MovieDetails> = _movieDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading



    private suspend fun loadRandomMoviePoster() {
        try {
            val details = movieRepository.getRandomMoviePosterWithDetails()
            _movieDetails.value = details

        } catch (e: Exception) {
            Log.e("FeedScreenViewModel", "Error loading poster: ${e.message}", e)
            _movieDetails.value =
                MovieDetails(id = "", name = "Loading", poster = ImageSource.Remote(url = ""), year = 0, country = "", genres = emptyList())
        }
    }
    init {
        viewModelScope.launch(Dispatchers.IO)  {
            try {
                loadRandomMoviePoster()
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }

}