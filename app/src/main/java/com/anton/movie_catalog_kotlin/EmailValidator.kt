package com.anton.movie_catalog_kotlin

class EmailValidator {

    fun isValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun getErrorMessage(email: String): String? {
        if (!isValid(email)) {
            return "Неверный формат электронной почты."
        }
        return null
    }
}