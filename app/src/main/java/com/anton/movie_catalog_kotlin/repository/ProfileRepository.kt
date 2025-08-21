package com.anton.movie_catalog_kotlin.repository

import android.util.Log
import com.anton.movie_catalog_kotlin.models.ProfileModel
import com.anton.movie_catalog_kotlin.networking.MovieCatalogApi


interface ProfileRepository {
    suspend fun getProfile()
    suspend fun putProfile(body: ProfileModel)

}

class ProfileRepositoryImpl(private val movieCatalogApi: MovieCatalogApi) :
    ProfileRepository {
    override suspend fun getProfile() {
        try {
            movieCatalogApi.getProfile()
        } catch (e: Exception) {
            Log.e("getProfile", "$e")
        }
    }

    override suspend fun putProfile(body: ProfileModel) {
        try {
            movieCatalogApi.putProfile(body)
        } catch (e: Exception) {
            Log.e("putProfile", "$e")
        }
    }

}