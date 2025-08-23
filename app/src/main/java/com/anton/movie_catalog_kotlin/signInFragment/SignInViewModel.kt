package com.anton.movie_catalog_kotlin.signInFragment

import androidx.lifecycle.ViewModel
import com.anton.movie_catalog_kotlin.signin.SignInUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel() : ViewModel() {


    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _isButtonEnable = MutableStateFlow(false)
    val isButtonEnable: StateFlow<Boolean> = _isButtonEnable

    private var login: String? = null
    private var password: String? = null

    fun onPasswordTextChanged(text: CharSequence?) {
        if (text == null) return
        password = text.toString()
    }

    fun onLoginTextChanged(text: CharSequence?) {
        if (text == null) return
        login = text.toString()
    }





}