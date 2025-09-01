package com.anton.movie_catalog_kotlin.utils

import javax.inject.Inject

class PasswordValidator @Inject constructor() {

    fun isValid (password: String, confirmPassword: String): Boolean {
        return password.isNotEmpty() && password == confirmPassword
    }

}