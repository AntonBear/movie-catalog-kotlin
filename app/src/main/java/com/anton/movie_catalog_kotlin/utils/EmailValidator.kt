package com.anton.movie_catalog_kotlin.utils

class EmailValidator {

    fun isValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}