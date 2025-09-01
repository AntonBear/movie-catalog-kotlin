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

sealed class SignInState {
    data object Initial : SignInState()
    data object Loading : SignInState()
    class LoginError(val message: String) : SignInState()
    class PasswordError(val message: String): SignInState()
    class LoginValidSuccess(): SignInState()
    class PasswordValidSuccess(): SignInState()
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    val loginValidator: LoginValidator,
    val passwordValidator: PasswordValidator,
    val kreosoftApi: KreosoftApi,
) : ViewModel() {

    private var _userLogin: String? = null
    private var _userPassword: String? = null

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Initial)
    val signInState: StateFlow<SignInState> = _signInState

    private val _userLogSuccess = MutableSharedFlow<Boolean>()
    val userLogSuccess: SharedFlow<Boolean> = _userLogSuccess

    private val _loginErrorText = MutableStateFlow<String?>(null)
    val loginErrorText: StateFlow<String?> = _loginErrorText

    private val _passwordErrorText = MutableStateFlow<String?>(null)
    val passwordErrorText: StateFlow<String?> = _passwordErrorText

    private val _passwordIsValid = MutableStateFlow<Boolean>(false)
    private val _loginIsValid = MutableStateFlow<Boolean>(false)

    val isSignInButtonEnable: StateFlow<Boolean> = combine(
        _passwordIsValid,
        _loginIsValid,
    ) { loginErr, passErr ->
        _loginIsValid.value && _passwordIsValid.value
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun onPasswordTextChanged(inputPassword: CharSequence?) {
        Log.d(
            "debug",
            "isPasswordValid - ${passwordValidator.signInPasswordIsValid(inputPassword)}"
        )
        if (!passwordValidator.signInPasswordIsValid(inputPassword)) {
            _passwordErrorText.value = "password error"
            _passwordIsValid.value = false
        } else {
            _passwordErrorText.value = null
            _passwordIsValid.value = true
            _userPassword = inputPassword.toString()
        }
    }

    fun onLoginTextChanged(inputLogin: CharSequence?) {
        Log.d("debug", "isLoginValid - ${loginValidator.isValid(inputLogin)}")
        if (loginValidator.isValid(inputLogin)) {
            _loginErrorText.value = "login error"
            _loginIsValid.value = false
        } else {
            _loginErrorText.value = null
            _loginIsValid.value = true
            _userLogin = inputLogin.toString()
        }
    }

    fun loginUser() {
        val request: LoginRequest =
            LoginRequest(username = _userLogin ?: "null", password = _userPassword ?: "null")
        viewModelScope.launch {
            val response = kreosoftApi.login(request)
            if (response.isSuccessful) {
                _userLogSuccess.emit(true)
                val body = response.body()
                if(body != null)
                Log.d("debug","token ${body.token}")
            } else {
                _userLogSuccess.emit(false)
            }
        }
    }
}
