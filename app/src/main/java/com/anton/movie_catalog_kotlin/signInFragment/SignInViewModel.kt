package com.anton.movie_catalog_kotlin.signInFragment

import androidx.lifecycle.ViewModel
import com.anton.movie_catalog_kotlin.signin.SignInUiState
import com.anton.movie_catalog_kotlin.utils.EmailValidator
import com.anton.movie_catalog_kotlin.utils.LoginValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class SignInViewModel(val loginValidator: LoginValidator = LoginValidator()) : ViewModel() {


    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isButtonEnable = MutableStateFlow(true)
    val isButtonEnable: StateFlow<Boolean> = _isButtonEnable

    private var login: String? = null
    private var password: String? = null

    private fun changeButtonEnableState() {
        _isButtonEnable.value = !_isButtonEnable.value
    }

    private fun isLoginValid(login: String?): Boolean {
        return loginValidator.isValid(login)
    }

    fun onPasswordTextChanged(text: CharSequence?) {
        if (text == null) return
        password = text.toString()
    }

    fun onLoginTextChanged(inputLogin: CharSequence?) {
        if (inputLogin == null) return
        login = inputLogin.toString()
        if (!isLoginValid(login))  {
            _error.value = "login error"
        }
    }





}