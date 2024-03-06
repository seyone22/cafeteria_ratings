package com.seyone22.cafeteriaRatings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.seyone22.cafeteriaRatings.ui.screen.RatingsStore
import kotlinx.coroutines.flow.map

const val RATINGS_DATASTORE ="ratings_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = RATINGS_DATASTORE)

class DataStoreManager (private val context: Context) {

    companion object {
        val RATING_COUNT = stringPreferencesKey("RATING_COUNT")
        val RATING_SUM = stringPreferencesKey("RATING_SUM")
    }

    suspend fun saveToDataStore(ratingsStore: RatingsStore) {
        context.dataStore.edit {
            it[RATING_SUM] = ratingsStore.totalRatingScore.toString()
            it[RATING_COUNT] = ratingsStore.ratingCount.toString()
        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        RatingsStore(
            totalRatingScore = (it[RATING_SUM]?:"0").toInt(),
            ratingCount = (it[RATING_COUNT]?:"0").toInt(),
        )
    }

    suspend fun clearDataStore() = context.dataStore.edit {
        it.clear()
    }

}