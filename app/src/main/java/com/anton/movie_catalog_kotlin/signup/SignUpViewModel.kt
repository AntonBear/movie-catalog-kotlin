package com.anton.movie_catalog_kotlin.signup

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.movie_catalog_kotlin.EmailValidator
import com.anton.movie_catalog_kotlin.PasswordValidator
import com.anton.movie_catalog_kotlin.models.Gender
import com.anton.movie_catalog_kotlin.models.SingUpRequest
import com.anton.movie_catalog_kotlin.networking.MovieCatalogApi
import com.anton.movie_catalog_kotlin.services.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingUpViewModel(
    private val movieCatalogApi: MovieCatalogApi = ServiceLocator.movieCatalogApi,
    private val emailValidator: EmailValidator = EmailValidator()
): ViewModel() {
    var selectedGender: Gender? = Gender.MALE

    fun singUp(
        userName: String,
        name: String,
        passWord: String,
        confirmPassword: String,
        email: String,
        birthDate: String,
        selectedGender: Gender,
        context: Context
    ) {

        viewModelScope.launch {
            println(userName)
            println(email)
            println(name)
            println(passWord)
            println(birthDate)
            println(selectedGender)


            val passwordValidator = PasswordValidator()
            val isValidPassword = passwordValidator.isValid(passWord, confirmPassword)
            val emailError = emailValidator.getErrorMessage(email)
            if (emailError != null) {
                withContext(Dispatchers.Main) {
                    showErrorMessage(context, emailError)
                }
                return@launch
            }

            if (isValidPassword) {
                val singUpRequest = SingUpRequest(
                    userName,
                    name,
                    passWord,
                    email,
                    birthDate,
                    selectedGender
                )
                try {
                    val response = movieCatalogApi.singUp(singUpRequest)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            println(it.token)
                        }
                    } else {  withContext(Dispatchers.Main) {
                        showErrorMessage(context, "Имя уже занято".toString())
                    }
                    }

                } catch (e: Exception) {
                    println(e)
                    println("Ошибка: ${e.message}")
                }
            }
            else {
                val errorMessage = passwordValidator.getErrorMessage(passWord, confirmPassword)
                    ?: "Ошибка валидации пароля"
                showErrorMessage(context, errorMessage)
            }
        }
    }
    private fun showErrorMessage(context: Context, message: String) {
        AlertDialog.Builder(context)
            .setTitle("Ошибка")
            .setMessage(message)
            .setPositiveButton("ОК") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}

