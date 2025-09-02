package com.anton.movie_catalog_kotlin.utils

import javax.inject.Inject

class EmailValidator @Inject constructor() {

    fun isValid(email: CharSequence?): Boolean {
        if (email.isNullOrEmpty()) return false
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}