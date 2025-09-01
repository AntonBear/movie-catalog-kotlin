package com.anton.movie_catalog_kotlin.mainActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.movie_catalog_kotlin.retrofit.KreosoftApi
import com.anton.movie_catalog_kotlin.storage.TokenStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import models.LoginRequest
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val kreosoftApi: KreosoftApi,
    private val tokenStorage: TokenStorage,
) : ViewModel() {

    private val _loginResult = MutableLiveData<String?>()
    val loginResult: LiveData<String?> = _loginResult

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            val response = kreosoftApi.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                response.body()?.token?.let { token ->
                    tokenStorage.saveToken(token)
                    Log.d("debug","token $token")
                    _loginResult.postValue(token)
                }
            } else {
                Log.d("debug","token ${response.errorBody()}")
                _loginResult.postValue(null)
            }
        }
    }
}
