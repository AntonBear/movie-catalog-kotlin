package com.anton.movie_catalog_kotlin.signUpFragment

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.text.Editable
import androidx.lifecycle.ViewModel
import com.anton.movie_catalog_kotlin.models.Gender
import com.anton.movie_catalog_kotlin.utils.EmailValidator
import com.anton.movie_catalog_kotlin.utils.LoginValidator
import com.anton.movie_catalog_kotlin.utils.PasswordValidator
import com.anton.movie_catalog_kotlin.utils.UserNameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val userNameValidator: UserNameValidator,
    val emailValidator: EmailValidator,
    val loginValidator: LoginValidator,
    val passwordValidator: PasswordValidator,
) : ViewModel() {

    // Сырые данные полей ввода
    private var _userLogin: String? = null
    private var _email: String = ""
    private var _userName: String? = null
    private var _password: String? = null
    private var _confirmPassword: String? = null
    private var _gender: Int? = null
    private var _birthDayRaw: String? = null

    private val _birthDate = MutableStateFlow<String?>(null)
    val birthDate: StateFlow<String?> = _birthDate

    // Стейты для ошибок полей ввода
    private val _errorLogin = MutableStateFlow<String?>(null)
    val errorLogin: StateFlow<String?> = _errorLogin

    private val _errorMail = MutableStateFlow<String?>(null)
    val errorMail: StateFlow<String?> = _errorMail

    private val _errorUserName = MutableStateFlow<String?>(null)
    val errorUserName: StateFlow<String?> = _errorUserName

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> = _confirmPasswordError

    private val _birthdayDateError = MutableStateFlow<String?>(null)
    val birthdayDateError: StateFlow<String?> = _birthdayDateError


    fun onUserLoginInputChanged(userLoginInput: CharSequence?) {
        if (loginValidator.isValid(userLoginInput)) {
            _errorLogin.value = null
            _userLogin = userLoginInput.toString()
        } else {
            _errorLogin.value = "Поле должно быть заполнено"
        }
    }

    fun updateEmailText(emailUserInput: CharSequence?) {
        _email = emailUserInput?.toString() ?: ""
    }

    fun emailUserOnFocusValid(emailUserInput: CharSequence?) {
        if (emailValidator.isValid(emailUserInput)) {
            _errorMail.value = null
        } else {
            _errorMail.value = "Поле заполнено некорректно"

        }
    }

    fun onUserNameInputChanged(userNameInput: CharSequence?) {
        if (userNameValidator.isValid(userNameInput)) {
            _errorUserName.value = null
            _userName = userNameInput.toString()
        } else {
            _errorUserName.value = "Поле должно быть заполнено"

        }
    }

    fun onPasswordInputChanged(passwordInput: CharSequence?) {
        _password = passwordInput.toString()
        if (passwordValidator.signInPasswordIsValid(passwordInput)) {
            _passwordError.value = null
        } else {
            _passwordError.value = "Поле должно быть заполнено"
        }
    }

    fun onConfirmPasswordTextChanged(confirmPasswordInput: CharSequence?) {
        _confirmPassword = confirmPasswordInput?.toString()
        if (passwordValidator.signInPasswordIsValid(_confirmPassword)) {
            _confirmPasswordError.value = null
        } else {
            _confirmPasswordError.value = "Пароли не совпадают"
        }
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

    // Стейты кнопок выбора гендера и активации регистрации
    private val _isMaleGenderSelected = MutableStateFlow<Boolean>(false)
    val isMaleGenderSelected: StateFlow<Boolean> = _isMaleGenderSelected

    private val _isFemaleGenderSelected = MutableStateFlow<Boolean>(false)
    val isFemaleGenderSelected: StateFlow<Boolean> = _isFemaleGenderSelected

    private val _isSignUpButtonEnabled = MutableStateFlow<Boolean>(false)
    val isSignUpButtonEnabled: StateFlow<Boolean> = _isSignUpButtonEnabled


    fun onBirthDateInputChanged(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        _birthDayRaw = formattedDate

        val locale = Locale("ru", "RU")
        val formattedDateUI =
            SimpleDateFormat("dd MMMM yyyy", locale)
                .format(Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                }.time)
        _birthDate.value = formattedDateUI
    }

}