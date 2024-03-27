package com.seyone22.cafeteriaRatings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val SECURE_DATASTORE ="secure_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SECURE_DATASTORE)

class SecureDataStoreManager(private val context: Context) {
    companion object {
        const val API_KEY = "API_KEY"
        const val ALLOW_AUTO = "ALLOW_AUTO"
        const val SITE = "SITE"
    }

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        SECURE_DATASTORE,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    suspend fun saveToDataStore(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getFromDataStore(key: String): Flow<String> = flow {
        emit(sharedPreferences.getString(key, "") ?: "")
    }

    suspend fun clearDataStore() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}