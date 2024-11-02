package com.anton.movie_catalog_kotlin

class PasswordValidator {

    fun isValid(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun getErrorMessage(password: String, confirmPassword: String): String? {
        if (password != confirmPassword) {
            return "Пароли не совпадают."
        }
        return null
    }

}