package com.anton.movie_catalog_kotlin.signInFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.movie_catalog_kotlin.retrofit.KreosoftApi
import com.anton.movie_catalog_kotlin.utils.LoginValidator
import com.anton.movie_catalog_kotlin.utils.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import models.LoginRequest
import javax.inject.Inject

sealed class ValidatorResult {
    class LoginSuccess : ValidatorResult()
    class PasswordSuccess : ValidatorResult()
    class LoginError : ValidatorResult()
    class PasswordError : ValidatorResult()
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    val loginValidator: LoginValidator,
    val passwordValidator: PasswordValidator,
    val kreosoftApi: KreosoftApi,
) : ViewModel() {

    private var userLogin: String? = null
    private var userPassword: String? = null

    private val _userLogSuccess = MutableSharedFlow<Boolean>()
    val userLogSuccess: SharedFlow<Boolean> = _userLogSuccess

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    //    private val _isSignInButtonEnable = MutableStateFlow(false)
    val isSignInButtonEnable: StateFlow<Boolean> = combine(
        _passwordError,
        _loginError,
    ) { loginErr, passErr ->
        loginErr == null && passErr == null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    private fun isLoginValid(login: String?): Boolean {
        return loginValidator.isValid(login)
    }

    fun onPasswordTextChanged(inputPassword: CharSequence?) {
        Log.d(
            "debug",
            "isPasswordValid - ${passwordValidator.signInPasswordIsValid(inputPassword)}"
        )
        if (!passwordValidator.signInPasswordIsValid(inputPassword)) {
            _passwordError.value = "password error"
        } else _passwordError.value = null
    }

    fun onLoginTextChanged(inputLogin: CharSequence?) {
        userLogin = inputLogin.toString()
        Log.d("debug", "isLoginValid - ${isLoginValid(userLogin)}")
        if (!isLoginValid(userLogin)) {
            _loginError.value = "login error"
        } else _loginError.value = null
    }

    fun loginUser() {
        val request: LoginRequest =
            LoginRequest(username = userLogin ?: "null", password = userPassword ?: "null")
        viewModelScope.launch {
            val fuu = kreosoftApi.login(request)
            if (fuu.isSuccessful) {
                _userLogSuccess.emit(true)
            } else {
                _userLogSuccess.emit(false)
            }
        }
    }
}
