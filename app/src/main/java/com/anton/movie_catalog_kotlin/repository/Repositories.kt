package com.anton.movie_catalog_kotlin.repository

import android.content.Context
import com.anton.movie_catalog_kotlin.MovieCatalogApplication
import com.anton.movie_catalog_kotlin.networking.ApiServices
import com.anton.movie_catalog_kotlin.storage.SecureTokenStorage
import com.anton.movie_catalog_kotlin.storage.TokenStorage

object Repositories {

    private val applicationContext: Context
        get() = MovieCatalogApplication.applicationContext

    private val tokenStorage: TokenStorage by lazy {
        SecureTokenStorage(applicationContext)
    }

    val userAuthRepository: UserAuthRepository by lazy {
        UserAuthRepositoryImpl(ApiServices.movieCatalogApi, tokenStorage)
    }


}
