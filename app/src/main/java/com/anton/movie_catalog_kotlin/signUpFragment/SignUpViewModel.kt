package com.anton.movie_catalog_kotlin.signUpFragment

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.movie_catalog_kotlin.models.Gender
import com.anton.movie_catalog_kotlin.models.SignUpRequest
import com.anton.movie_catalog_kotlin.retrofit.KreosoftRepository
import com.anton.movie_catalog_kotlin.storage.TokenStorage
import com.anton.movie_catalog_kotlin.utils.EmailValidator
import com.anton.movie_catalog_kotlin.utils.LoginValidator
import com.anton.movie_catalog_kotlin.utils.PasswordValidator
import com.anton.movie_catalog_kotlin.utils.UserNameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val kreosoftRepository: KreosoftRepository,
    val userNameValidator: UserNameValidator,
    val emailValidator: EmailValidator,
    val loginValidator: LoginValidator,
    val passwordValidator: PasswordValidator,
    val tokenStorage: TokenStorage,
) : ViewModel() {

    // Сырые данные полей ввода
    private var _userLogin: String = ""
    private var _email: String = ""
    private var _userName: String = ""
    private var _password: String = ""
    private var _confirmPassword: String = ""
    private var _gender: Int = 0
    private var _birthDateRaw: String = ""

    // UI стейт для дня рождения
    private val _birthDate = MutableStateFlow<String?>(null)
    val birthDate: StateFlow<String?> = _birthDate

    //Стейт для перехода на фрагмент при успешной регистрации
    private val _navigateToNextFragment = MutableSharedFlow<Unit>()
    val navigateToNextFragment: SharedFlow<Unit> = _navigateToNextFragment

    // Стейты валидности полей
    private val _userLoginIsValid = MutableStateFlow<Boolean>(false)
    private val _emailIsValid = MutableStateFlow<Boolean>(false)
    private val _userNameIsValid = MutableStateFlow<Boolean>(false)
    private val _passwordIsValid = MutableStateFlow<Boolean>(false)
    private val _confirmIsValid = MutableStateFlow<Boolean>(false)
    private val _genderIsValid = MutableStateFlow<Boolean>(false)
    private val _birthDateIsValid = MutableStateFlow<Boolean>(false)

    // Стейты для текста ошибок
    private val _errorLogin = MutableStateFlow<String?>(null)
    val errorLogin: StateFlow<String?> = _errorLogin

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _errorUserName = MutableStateFlow<String?>(null)
    val errorUserName: StateFlow<String?> = _errorUserName

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> = _confirmPasswordError

    private val _birthdayDateError = MutableStateFlow<String?>(null)
    val birthdayDateError: StateFlow<String?> = _birthdayDateError

    fun onUserLoginInputChanged(userLoginInput: CharSequence?) {
        _userLogin = userLoginInput.toString()
        if (loginValidator.isValid(_userLogin)) {
            _errorLogin.value = null
            _userLoginIsValid.value = true
        } else {
            _errorLogin.value = "Поле должно быть заполнено"
        }
    }

    fun updateEmailText(emailUserInput: CharSequence?) {
        _email = emailUserInput?.toString() ?: ""
        if (emailValidator.isValid(_email)) {
            _emailError.value = null
            _emailIsValid.value = true
        } else {
            _emailError.value = "Неправильный емаил"
        }
    }


    fun onUserNameInputChanged(userNameInput: CharSequence?) {
        _userName = userNameInput.toString()
        if (userNameValidator.isValid(_userName)) {
            _errorUserName.value = null
            _userNameIsValid.value = true
        } else {
            _errorUserName.value = "Поле заполнено не верно"
        }
    }

    fun onPasswordInputChanged(passwordInput: CharSequence?) {
        _password = passwordInput.toString()
        if (passwordValidator.passwordIsValid(_password)) {
            _passwordError.value = null
            _passwordIsValid.value = true
        } else {
            _passwordError.value = "Поле заполнено не верно"
        }
    }

    fun checkPasswordsMatch() {
        if (_password.isNullOrBlank() || _confirmPassword.isNullOrBlank()) {
            return
        }
        if (passwordValidator.checkPasswordsMatch(_password, _confirmPassword)) {
            _confirmPasswordError.value = null
        } else {
            _confirmPasswordError.value = "Пароли не совпадают"
        }
    }

    fun onConfirmPasswordTextChanged(confirmPasswordInput: CharSequence?) {
        _confirmPassword = confirmPasswordInput.toString()
        if (passwordValidator.passwordIsValid(_confirmPassword)) {
            _confirmPasswordError.value = null
            _confirmIsValid.value = true
        } else {
            _confirmPasswordError.value = "Поле заполнено не верно"
        }
    }

    fun onConfirmPasswordFocusLost() {
        if (passwordValidator.checkPasswordsMatch(_password, _confirmPassword)) {
            _confirmPasswordError.value = null
        } else {
            _confirmPasswordError.value = "Пароли не совпадают"
        }
    }
    fun onUserLoginFocusLost() {
        if(loginValidator.isValid(_userLogin)) _errorLogin.value = null else _errorLogin.value = "Поле заполнено не верно"
    }

    fun onUserNameFocusLost() {
        if (userNameValidator.isValid(_userName)) _errorUserName.value = null else _errorUserName.value = "Полне заполнено не верно"
    }

    fun onPasswordFocusLost() {
        if (passwordValidator.passwordIsValid(_password)) _passwordError.value = null else _passwordError.value = "Полне заполнено не верно"
    }

    fun onFemaleGenderChanged() {
        _isFemaleGenderSelected.value = true
        _isMaleGenderSelected.value = false
        _genderIsValid.value = true
        _gender = 0
    }

    fun onMaleGenderChanged() {
        _isMaleGenderSelected.value = true
        _isFemaleGenderSelected.value = false
        _genderIsValid.value = true
        _gender = 1
    }



    // Стейты кнопок выбора гендера и активации регистрации
    private val _isMaleGenderSelected = MutableStateFlow<Boolean>(false)
    val isMaleGenderSelected: StateFlow<Boolean> = _isMaleGenderSelected

    private val _isFemaleGenderSelected = MutableStateFlow<Boolean>(false)
    val isFemaleGenderSelected: StateFlow<Boolean> = _isFemaleGenderSelected
//
//    private val _isSignUpButtonEnabled = MutableStateFlow<Boolean>(false)
//    val isSignUpButtonEnabled: StateFlow<Boolean> = _isSignUpButtonEnabled


    fun onBirthDateInputChanged(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        _birthDateRaw = formattedDate
        _birthDateIsValid.value = true

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

    fun registerUser() {
        val request = SignUpRequest(
            userName = _userName,
            name = _userLogin,
            password = _password,
            email = _email,
            birthDate = _birthDateRaw,
            gender = _gender )

        Log.d("debug", "$_userName, $_userLogin, $_password, $_email, $_birthDateRaw, $_gender")
        viewModelScope.launch {
            val result = kreosoftRepository.regUser(request)

            result.onSuccess { token ->
                println("Регистрация успешна, токен: $token")
                tokenStorage.saveToken(token)
                _navigateToNextFragment.emit(Unit)
            }.onFailure { e ->
                println("Ошибка регистрации: ${e.message}")
            }
        }
    }



    val isSignUpButtonEnabled: StateFlow<Boolean> = combine(
        _userLoginIsValid,
        _emailIsValid,
        _userNameIsValid,
        _passwordIsValid,
        _confirmIsValid,
        _genderIsValid,
        _birthDateIsValid,
    ) { values: Array<Boolean> ->
        values.all { it }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    init {
        _userLogin = "TestLogin"
        _email = "test@example.com"
        _userName = "User_" + UUID.randomUUID().toString().take(8) // случайный каждый раз
        _password = "greedisgood"
        _confirmPassword = "greedisgood"
        _gender = 0 // например, male
        _birthDateRaw = "2000-01-01T00:00:00.000Z"

        // UI-friendly дата
        _birthDate.value = "01 января 2000"

        // все валидные
        _userLoginIsValid.value = true
        _emailIsValid.value = true
        _userNameIsValid.value = true
        _passwordIsValid.value = true
        _confirmIsValid.value = true
        _genderIsValid.value = true
        _birthDateIsValid.value = true

        _isMaleGenderSelected.value = true
    }

}