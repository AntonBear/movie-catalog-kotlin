package com.anton.movie_catalog_kotlin

import android.app.Application
import android.content.Context

class MovieCatalogApplication : Application() {

    companion object {
        lateinit var applicationContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        MovieCatalogApplication.applicationContext = applicationContext
    }
}