package com.anton.movie_catalog_kotlin.feedScreenFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.movie_catalog_kotlin.models.MovieDetails
import com.anton.movie_catalog_kotlin.repository.Repositories.movieRepository
import kotlinx.coroutines.launch

class FeedScreenFragmentViewModel : ViewModel() {
    private val _movieData = MutableLiveData<MovieDetails?>()
    val movieData: LiveData<MovieDetails?> = _movieData

    fun loadData() {
        viewModelScope.launch {
            val result = movieRepository.getRandomMoviePosterWithDetails()
            _movieData.value = result
        }
    }
}