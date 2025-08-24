package com.anton.movie_catalog_kotlin.signUpFragment

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.text.Editable
import androidx.lifecycle.ViewModel
import com.anton.movie_catalog_kotlin.models.Gender
import java.util.Locale

class SignUpViewModel() : ViewModel() {

    private var userLogin: String? = null
    private var email: String? = null
    private var userName: String? = null
    private var password: String? = null
    private var confirmPassword: String? = null
    private var birthDate: String? = null
    private var gender: Gender? = null

    fun onUserLoginInputChanged(userLoginInput: String?) {
        userLogin = userLoginInput.toString()
    }

    fun onEmailUserInputChanged(emailUserInput: String?) {
        email = emailUserInput.toString()
    }

    fun onUserNameInputChanged(userNameInput: String?) {
        userName = userNameInput.toString()
    }

    fun onPasswordInputChanged(passwordInput: String?) {
        password = passwordInput.toString()
    }

    fun onConfirmPasswordTextChanged(confirmPasswordInput: String?) {
        confirmPassword = confirmPasswordInput.toString()
    }

    fun onBirthDateInputChanged(birthDateInput: String?) {
        birthDate = birthDate.toString()
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

}