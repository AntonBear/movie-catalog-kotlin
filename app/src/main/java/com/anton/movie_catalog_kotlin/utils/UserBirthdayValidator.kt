package com.anton.movie_catalog_kotlin.utils

class UserBirthdayValidator() {

    fun isUserBirthdayValid(userBirthday: String?): Boolean {
        return userBirthday.isNullOrEmpty()
    }
}