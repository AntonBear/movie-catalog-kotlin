package com.anton.movie_catalog_kotlin.utils

import javax.inject.Inject


class LoginValidator @Inject constructor() {
    fun isValid(login: CharSequence?): Boolean {
        return !login.isNullOrEmpty()
    }
}