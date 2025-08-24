package com.anton.movie_catalog_kotlin.utils

class LoginValidator() {
    fun isValid(login: String?): Boolean {
        return login?.isBlank() ?: false
    }
}