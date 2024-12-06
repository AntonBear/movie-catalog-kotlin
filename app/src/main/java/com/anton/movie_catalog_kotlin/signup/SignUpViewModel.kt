package com.anton.movie_catalog_kotlin.signup

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anton.movie_catalog_kotlin.models.Gender
import com.anton.movie_catalog_kotlin.models.SignUpRequest
import com.anton.movie_catalog_kotlin.repository.Repositories
import com.anton.movie_catalog_kotlin.repository.UserAuthRepository
import com.anton.movie_catalog_kotlin.utils.EmailValidator
import com.anton.movie_catalog_kotlin.utils.PasswordValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class SignUpViewModel(
    private val userAuthRepository: UserAuthRepository,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private var userName: String = ""
    private var name: String = ""
    private var password: String = ""
    private var confirmPassword: String = " "
    private var email: String = ""
    private var birthDate: String = ""
    private var gender: Gender? = null

    fun onGenderSelectedMale() {
        updateGender(Gender.MALE)
    }

    fun onGenderSelectedFemale() {
        updateGender(Gender.FEMALE)
    }

    private fun updateGender(gender: Gender) {
        this.gender = gender
        _uiState.update {
            it.copy(
                isMaleSelected = gender == Gender.MALE,
                isFemaleSelected = gender == Gender.FEMALE,
            )
        }
        updateButtonState()
    }

    fun onPasswordTextChanged(editable: Editable) {
        password = editable.toString()
        updateButtonState()
    }

    fun onConfirmPasswordTextChanged(editable: Editable) {
        confirmPassword = editable.toString()
        updateButtonState()
    }

    fun onLoginTextChanged(editable: Editable) {
        userName = editable.toString()
        updateButtonState()
    }

    fun onEmailTextChanged(editable: Editable) {
        email = editable.toString()
        updateButtonState()
    }

    fun onNameTextChanged(editable: Editable) {
        name = editable.toString()
        updateButtonState()
    }

    fun onBirthDateTextChanged(editable: Editable) {
        updateButtonState()
    }

    fun onDateSelected(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        birthDate = formattedDate
    }

    private fun updateButtonState() {
        val isEnabled = areFieldsNotEmpty()
        _uiState.value = _uiState.value.copy(isSignUpButtonEnabled = isEnabled)
    }

    private fun areInputFieldsValid(): Boolean {
        val fieldsNotEmpty = areFieldsNotEmpty()
        val isEmailValid = emailValidator.isValid(email)
        val isPasswordValid = passwordValidator.isValid(password, confirmPassword)
        return fieldsNotEmpty && isEmailValid && isPasswordValid
    }

    private fun areFieldsNotEmpty(): Boolean {
        val fieldsNotEmpty = listOf(
            userName,
            password,
            confirmPassword,
            name,
            email,
            birthDate
        ).all(String::isNotBlank)
        val genderNotEmpty = gender != null
        return fieldsNotEmpty && genderNotEmpty
    }

    fun signUp() {
        if (_uiState.value.isLoading) return

        val isValid = areInputFieldsValid()

        if (!isValid) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = SignUpError.InvalidFields
            )
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val signUpRequest = SignUpRequest(
                    userName,
                    name,
                    password,
                    email,
                    birthDate,
                    gender!!,
                )
                userAuthRepository.signUp(signUpRequest)
                _uiState.update { it.copy(isUserLoggedIn = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = SignUpError.RequestError(e.message)) }
            } finally {
                _uiState.update { it.copy(isLoading = false, error = null)  }
            }
        }
    }

    fun onHandleError() {
        _uiState.update { it.copy(error = null) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SignUpViewModel(
                    userAuthRepository = Repositories.userAuthRepository,
                    emailValidator = EmailValidator(),
                    passwordValidator = PasswordValidator()
                )
            }
        }
    }
}



