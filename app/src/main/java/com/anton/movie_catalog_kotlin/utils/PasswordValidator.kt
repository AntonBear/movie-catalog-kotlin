package com.anton.movie_catalog_kotlin.utils

import javax.inject.Inject

class PasswordValidator @Inject constructor() {

    fun signUpPasswordIsValid(password: String?, confirmPassword: String?): Boolean {
        if (password.isNullOrEmpty()) return false
        if (confirmPassword.isNullOrEmpty()) return false
        return password == confirmPassword
    }

    fun confirmPasswordValid(password: String?, confirmPassword: String?): Boolean {
        return password == confirmPassword
    }

    fun passwordIsValid(password: CharSequence?): Boolean {
        return !password.isNullOrEmpty()
    }
}