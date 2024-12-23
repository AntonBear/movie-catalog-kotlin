package com.anton.movie_catalog_kotlin.feedScreenFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.movie_catalog_kotlin.repository.Repositories.movieRepository
import kotlinx.coroutines.launch

class FeedScreenFragmentViewModel: ViewModel() {

    val imageUrl = MutableLiveData<String>()
    val genres = MutableLiveData<List<String>>()
    val name = MutableLiveData<String>()
    val year = MutableLiveData<String>()
    val country = MutableLiveData<String>()
    private val _movieId = MutableLiveData<String>()
    val movieId: LiveData<String> = _movieId


    fun loadData() {
        viewModelScope.launch {
            val result = movieRepository.getRandomMoviePosterWithDetails()
            Log.d("FeedScreenViewModel", "Received movie id: ${result.id}")
            _movieId.value = result.id
            Log.d("FeedScreenViewModel", "Movie id after conversion: ${_movieId.value}")
            imageUrl.value = result.poster
            name.value = result.name
            year.value = result.year.toString()
            val countries = result.country.split(",").map { it.trim() }
            country.value = countries.firstOrNull() ?: ""
            genres.value = result.genres
        }
    }
}