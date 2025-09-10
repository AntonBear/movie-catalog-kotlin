package com.anton.movie_catalog_kotlin.feedScreenFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.movie_catalog_kotlin.models.MovieDetails
import com.anton.movie_catalog_kotlin.repository.Repositories.movieRepository
import com.anton.movie_catalog_kotlin.retrofit.KreosoftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenFragmentViewModel @Inject constructor(
    val kreosoftRepository: KreosoftRepository,
) : ViewModel() {

    private val _movieData = MutableStateFlow<MovieDetails?>(null)
    val movieData: StateFlow<MovieDetails?> = _movieData

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val response = kreosoftRepository.getRandomMovieDetails()
            if (response.isSuccess) {
                response.map { it ->
                    _movieData.value = it
                }
            } else {
                Log.d("debug", "${response}")
                _movieData.value = MovieDetails(
                    id = "Загрузка",
                    name = "Загрузка",
                    poster = "Загрузка",
                    year = 2025,
                    country = "Загрузка",
                    genres = emptyList(),
                )
            }
        }
    }
}
