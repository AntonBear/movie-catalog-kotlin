package com.anton.movie_catalog_kotlin.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

interface TokenStorage {
    fun getApiKey(): String?
    fun saveApiKey(apiKey: String?)
}

class SecureTokenStorage(context: Context): TokenStorage {
    private companion object {
        const val PREFS_NAME = "token_prefs"
        const val KEY_API_AUTH_TOKEN = "apiAuthToken"
    }

    private val prefs: EncryptedSharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }

    override fun getApiKey(): String? = prefs.getString(KEY_API_AUTH_TOKEN, null)

    override fun saveApiKey(apiKey: String?) {
        prefs.edit().putString(KEY_API_AUTH_TOKEN, apiKey).apply()
    }
}