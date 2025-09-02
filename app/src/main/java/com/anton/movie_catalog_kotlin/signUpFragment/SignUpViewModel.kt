package com.anton.movie_catalog_kotlin.signUpFragment

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.text.Editable
import androidx.lifecycle.ViewModel
import com.anton.movie_catalog_kotlin.models.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    // Стейты полей ввода
    private var _userLogin: String? = null
    private var _email: String? = null
    private var _userName: String? = null
    private var _password: String? = null
    private var _confirmPassword: String? = null
    private var _birthDate: String? = null
    private var _gender: Int? = null


    // Стейты для ошибок полей ввода
    private val _errorLogin = MutableStateFlow<String?>(null)
    val errorLogin: StateFlow<String?> = _errorLogin

    private val _errorMail = MutableStateFlow<String?>(null)
    val errorMail: StateFlow<String?> = _errorLogin

    private val _errorUserName = MutableStateFlow<String?>(null)
    val errorUserName: StateFlow<String?> = _errorUserName

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> = _confirmPasswordError

    private val _birthdayDate = MutableStateFlow<String?>(null)
    val birthdayDate: StateFlow<String?> = _birthdayDate



    fun onUserLoginInputChanged(userLoginInput: CharSequence?) {
        _userLogin = userLoginInput.toString()
    }

    fun onEmailUserInputChanged(emailUserInput: CharSequence?) {
        _email = emailUserInput.toString()
    }

    fun onUserNameInputChanged(userNameInput: CharSequence?) {
        _userName = userNameInput.toString()
    }

    fun onPasswordInputChanged(passwordInput: CharSequence?) {
        _password = passwordInput.toString()
    }

    fun onConfirmPasswordTextChanged(confirmPasswordInput: CharSequence?) {
        _confirmPassword = confirmPasswordInput.toString()
    }

    fun onBirthDateInputChanged(birthDateInput: CharSequence?) {
        _birthDate = birthDateInput.toString()
    }

    fun onFemaleGenderChanged() {
        _isFemaleGenderSelected.value = true
        _isMaleGenderSelected.value = false
        _gender = 0
    }

    fun onMaleGenderChanged() {
        _isMaleGenderSelected.value = true
        _isFemaleGenderSelected.value = false
        _gender = 1
    }

    private val _isMaleGenderSelected = MutableStateFlow<Boolean>(false)
    val isMaleGenderSelected: StateFlow<Boolean> = _isMaleGenderSelected

    private val _isFemaleGenderSelected = MutableStateFlow<Boolean>(false)
    val isFemaleGenderSelected: StateFlow<Boolean> = _isFemaleGenderSelected


    fun onDateSelected(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        _birthDate = formattedDate
    }

}