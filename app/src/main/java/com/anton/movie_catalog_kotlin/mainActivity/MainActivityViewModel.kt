package com.anton.movie_catalog_kotlin.mainActivity

import android.util.Log
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
    val kreosoftApi: KreosoftApi,
    val tokenStorage: TokenStorage,
) : ViewModel() {

    fun loginUser() {
        val request: LoginRequest =
            LoginRequest(username = "anton", password = "greedisgood")
        viewModelScope.launch {
            val response = kreosoftApi.login(request)
            if (response.isSuccessful) {
                val body = response.body()
                if(body != null)  {
                    tokenStorage.saveToken(body.token)
                    Log.d("debug","token ${body.token}")
                }
            } else {
                Log.d("debug","token ${response.errorBody()}")
            }
        }
    }

}