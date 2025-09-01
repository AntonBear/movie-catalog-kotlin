package com.anton.movie_catalog_kotlin.utils

import javax.inject.Inject

class UserNameValidator @Inject constructor() {
    fun isValid(userName: String?): Boolean {
        return userName?.isBlank() ?: false
    }
}