package com.anton.movie_catalog_kotlin.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anton.movie_catalog_kotlin.repository.AuthRepository
import com.anton.movie_catalog_kotlin.repository.AuthResult
import com.anton.movie_catalog_kotlin.repository.Repositories
import com.anton.movie_catalog_kotlin.utils.Result
import kotlinx.coroutines.launch
import models.LoginRequest

class SignInViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val TAG = "SignInViewModel"
    private val _loginResult = MutableLiveData<Result<Unit>>()
    val loginResult: LiveData<Result<Unit>> = _loginResult
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SignInViewModel(authRepository = Repositories.authRepository)
            }
        }
    }

    suspend fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(username, password)
                when (val authResult = authRepository.login(loginRequest)) {
                    is AuthResult.Success -> {
                        _loginResult.value = Result.Success(Unit)
                    }
                    is AuthResult.Error -> {
                        _loginResult.value = Result.Error(Exception(authResult.message))
                    }
                }
            } catch (e: Exception) {
                _loginResult.value = Result.Error(e)
                Log.e(TAG, e.message ?: "Error occurred")
            }
        }
    }
}


