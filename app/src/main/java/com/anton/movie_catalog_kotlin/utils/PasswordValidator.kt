package com.anton.movie_catalog_kotlin.utils

class PasswordValidator {

    fun isValid(password: String, confirmPassword: String): Boolean {
        return password.isNotEmpty() && password == confirmPassword
    }

}