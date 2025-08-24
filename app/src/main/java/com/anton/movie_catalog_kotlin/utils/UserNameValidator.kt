package com.anton.movie_catalog_kotlin.utils

class UserNameValidator() {
    fun isValid(userName: String?): Boolean {
        return userName?.isBlank() ?: false
    }
}