package com.anton.movie_catalog_kotlin.signup

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anton.movie_catalog_kotlin.models.Gender
import com.anton.movie_catalog_kotlin.models.SignUpRequest
import com.anton.movie_catalog_kotlin.repository.Repositories
import com.anton.movie_catalog_kotlin.repository.SignUpRepository
import com.anton.movie_catalog_kotlin.repository.SignUpResult
import com.anton.movie_catalog_kotlin.utils.EmailValidator
import com.anton.movie_catalog_kotlin.utils.PasswordValidator
import com.anton.movie_catalog_kotlin.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpRepository: SignUpRepository,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SignUpViewModel(
                    signUpRepository = Repositories.signUpRepository,
                    emailValidator = EmailValidator(),
                    passwordValidator = PasswordValidator()
                )
            }
        }
    }

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState

    sealed interface SignUpState {
        data object Idle : SignUpState
        data object Loading : SignUpState
        data object InputFieldsInvalid : SignUpState
        data object Success : SignUpState
        data class Error(val message: String) : SignUpState
    }

    private val _selectedGender = MutableStateFlow(Gender.MALE)
    val selectedGender: StateFlow<Gender> = _selectedGender

    fun selectGender(gender: Gender) {
        _selectedGender.value = gender
    }

    private fun validateInputFields(
        login: String, email: String, name: String, password: String,
        dateOfBirth: String, confirmPassword: String,
    ): Boolean {
        return with(emailValidator) {
            login.isNotBlank() && email.isNotBlank() && name.isNotBlank() && password.isNotBlank() &&
                    dateOfBirth.isNotBlank() && confirmPassword.isNotBlank() && isValid(email) &&
                    passwordValidator.isValid(password, confirmPassword)
        }
    }

    fun signUp(
        userName: String, name: String, password: String, confirmPassword: String,
        email: String, birthDate: String
    ) {
        if (_signUpState.value == SignUpState.Loading) return

        viewModelScope.launch {
            _signUpState.value = SignUpState.Loading

            val gender = selectedGender.value
            val isValid = validateInputFields(userName, email, name, password, birthDate, confirmPassword)

            if (isValid) {
                try {
                    val signUpRequest = SignUpRequest(userName, name, password, email, birthDate, gender)
                    when (val result = signUpRepository.signUp(signUpRequest)) {
                        is SignUpResult.Success -> _signUpState.value = SignUpState.Success
                        is SignUpResult.Error -> _signUpState.value = SignUpState.Error(result.message)
                    }
                } catch (e: Exception) {
                    _signUpState.value = SignUpState.Error(e.message ?: "An unexpected error occurred")
                    Log.e(TAG, e.message ?: "Error occurred")
                }
            } else {
                _signUpState.value = SignUpState.InputFieldsInvalid
            }
        }
    }
}



