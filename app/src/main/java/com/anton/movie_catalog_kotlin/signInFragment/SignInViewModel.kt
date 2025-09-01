package com.anton.movie_catalog_kotlin.signInFragment

import androidx.lifecycle.ViewModel
import com.anton.movie_catalog_kotlin.signin.SignInUiState
import com.anton.movie_catalog_kotlin.utils.EmailValidator
import com.anton.movie_catalog_kotlin.utils.LoginValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(val loginValidator: LoginValidator) : ViewModel() {


    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isSignInButtonEnable = MutableStateFlow(true)
    val isSignInButtonEnable: StateFlow<Boolean> = _isSignInButtonEnable

    private var login: String? = null
    private var password: String? = null

    private fun changeSignInButtonState() {
        _isSignInButtonEnable.value = !_isSignInButtonEnable.value
    }

    private fun isLoginValid(login: String?): Boolean {
        return loginValidator.isValid(login)
    }

    fun onPasswordTextChanged(inputPassoword: CharSequence?) {
        if (inputPassoword == null) return

        password = inputPassoword.toString()
    }

    fun onLoginTextChanged(inputLogin: CharSequence?) {
        if (inputLogin == null) return
        login = inputLogin.toString()
        if (!isLoginValid(login))  {
            _error.value = "login error"
        }
    }
}