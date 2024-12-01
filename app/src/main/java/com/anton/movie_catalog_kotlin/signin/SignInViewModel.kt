package com.anton.movie_catalog_kotlin.signin

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anton.movie_catalog_kotlin.repository.Repositories
import com.anton.movie_catalog_kotlin.repository.UserAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.LoginRequest

class SignInViewModel(
    private val userAuthRepository: UserAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private var login = ""
    private var password = ""

    fun onPasswordTextChanged(editable: Editable) {
        password = editable.toString()
        updateButtonState()
    }

    fun onLoginTextChanged(editable: Editable) {
        login = editable.toString()
        updateButtonState()
    }

    private fun updateButtonState() {
        val isEnabled = areFieldsNotEmpty()
        _uiState.update { it.copy(isAuthButtonEnabled = isEnabled) }
    }

    private fun areFieldsNotEmpty(): Boolean {
        return login.isNotBlank() && password.isNotBlank()
    }

    fun signIn() {

        if (!areFieldsNotEmpty()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val loginRequest = LoginRequest(login, password)
                userAuthRepository.signIn(loginRequest)
                _uiState.update { it.copy(isUserLoggedIn = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = SignInError.RequestError(e.message)) }
            } finally {
                _uiState.update { it.copy(isLoading = false, error = null) }
            }
        }
    }

    fun onHandleError() {
        _uiState.update { it.copy(error = null) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SignInViewModel(userAuthRepository = Repositories.userAuthRepository)
            }
        }
    }
}




