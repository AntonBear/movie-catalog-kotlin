package com.anton.movie_catalog_kotlin

import android.app.Application
import android.content.Context
import com.anton.movie_catalog_kotlin.room.AppDatabase
import com.anton.movie_catalog_kotlin.room.GenreDao
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieCatalogApplication : Application() {

    lateinit var database: AppDatabase
    lateinit var genreDao: GenreDao

    companion object {
        lateinit var applicationContext: Context
            private set
    }


    override fun onCreate() {
        super.onCreate()
        MovieCatalogApplication.applicationContext = applicationContext
        database = AppDatabase.getDatabase(this)
        genreDao = database.genreDao()
    }
}