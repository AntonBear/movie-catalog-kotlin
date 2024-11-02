package com.anton.movie_catalog_kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private var _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    fun startLoading() {
        viewModelScope.launch {
            _isLoading.postValue(false)
        }
    }
}