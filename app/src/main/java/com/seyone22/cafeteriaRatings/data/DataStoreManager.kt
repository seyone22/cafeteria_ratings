package com.seyone22.cafeteriaRatings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.seyone22.cafeteriaRatings.model.RatingsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val RATINGS_DATASTORE ="ratings_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = RATINGS_DATASTORE)

class DataStoreManager (private val context: Context) {
    companion object {
        val RATING_COUNT = stringPreferencesKey("RATING_COUNT")
        val RATING_SUM = stringPreferencesKey("RATING_SUM")
        val COUNT_SATISFIED = stringPreferencesKey("COUNT_SATISFIED")
        val COUNT_NEUTRAL = stringPreferencesKey("COUNT_NEUTRAL")
        val COUNT_DISSATISFIED = stringPreferencesKey("COUNT_DISSATISFIED")
        val BACKGROUND_URI = stringPreferencesKey("BACKGROUND_URI")
        val USE_CUSTOM_BACKGROUND = stringPreferencesKey("USE_CUSTOM_BACKGROUND")
        val HOME_GREETING = stringPreferencesKey("HOME_GREETING")
        val ALLOW_AUTO = stringPreferencesKey("ALLOW_AUTO")
        val API_KEY = stringPreferencesKey("API_KEY")
        val SHOW_DEBUG = stringPreferencesKey("SHOW_DEBUG")
        val SITE = stringPreferencesKey("SITE")
    }

    suspend fun saveRatingToDataStore(ratingsStore: RatingsStore) {
        context.dataStore.edit {
            it[RATING_SUM] = ratingsStore.totalRatingScore.toString()
            it[RATING_COUNT] = ratingsStore.ratingCount.toString()
            it[COUNT_SATISFIED] = ratingsStore.countSatisfied.toString()
            it[COUNT_NEUTRAL] = ratingsStore.countNeutral.toString()
            it[COUNT_DISSATISFIED] = ratingsStore.countDissatisfied.toString()
        }
    }

    suspend fun saveToDataStore(key: String, value: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    fun getFromDataStore(key: String): Flow<Any> {
        return when (key) {
            "RATINGS" -> context.dataStore.data.map { prefs ->
                RatingsStore(
                    totalRatingScore = (prefs[RATING_SUM] ?: "0").toFloat(),
                    ratingCount = (prefs[RATING_COUNT] ?: "0").toInt(),
                    countSatisfied = (prefs[COUNT_SATISFIED] ?: "0").toInt(),
                    countNeutral = (prefs[COUNT_NEUTRAL] ?: "0").toInt(),
                    countDissatisfied = (prefs[COUNT_DISSATISFIED] ?: "0").toInt(),
                    )
            }
            "BACKGROUND_URI" -> context.dataStore.data.map { prefs ->
                prefs[BACKGROUND_URI] ?: ""
            }
            "USE_CUSTOM_BACKGROUND" -> context.dataStore.data.map { prefs ->
                prefs[USE_CUSTOM_BACKGROUND] ?: "false"
            }
            "HOME_GREETING" -> context.dataStore.data.map { prefs ->
                prefs[HOME_GREETING] ?: "How was your meal?"
            }
            "ALLOW_AUTO" -> context.dataStore.data.map { prefs ->
                prefs[ALLOW_AUTO] ?: "false"
            }
            "API_KEY" -> context.dataStore.data.map { prefs ->
                prefs[API_KEY] ?: ""
            }
            "SHOW_DEBUG" -> context.dataStore.data.map { prefs ->
                prefs[SHOW_DEBUG] ?: "false"
            }
            "SITE" -> context.dataStore.data.map { prefs ->
                prefs[SITE] ?: ""
            }
            else -> throw IllegalArgumentException("Invalid key: $key")
        }
    }

    suspend fun clearDataStore() = context.dataStore.edit {
        it.clear()
    }
}