package com.seyone22.cafeteriaRatings.ui.screen.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.data.SecureDataStoreManager
import com.seyone22.cafeteriaRatings.data.externalApi.ExternalApi
import com.seyone22.cafeteriaRatings.getCurrentTimeInISO8601
import com.seyone22.cafeteriaRatings.model.Dissatisfied
import com.seyone22.cafeteriaRatings.model.Neutral
import com.seyone22.cafeteriaRatings.model.Rating
import com.seyone22.cafeteriaRatings.model.RatingsStore
import com.seyone22.cafeteriaRatings.model.Satisfied
import com.seyone22.cafeteriaRatings.model.add
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

class HomeViewModel : ViewModel() {
    val ratingsList = listOf(
        Satisfied,
        Neutral,
        Dissatisfied,
    )

    val currentSessionRatings: MutableStateFlow<RatingsStore> = MutableStateFlow(RatingsStore())

    suspend fun recordRating(
        rating: Rating,
        context : Context,
        dataStoreManager: DataStoreManager = DataStoreManager(context)
    ) : Boolean {
        try {
            val s = SecureDataStoreManager(context)

            currentSessionRatings.value.add(rating)
            dataStoreManager.saveRatingToDataStore(currentSessionRatings.value)


            // Push rating to server
            if (true) {
                Log.d("TAG", "HomeScreen inside: ")
                postReview(rating, s)
            }

            return true
        } catch (_: Exception) {
            return false
        }
    }
}

suspend fun postReview(
    rating : Rating,
    s : SecureDataStoreManager
) {
    try {
        val token = s.getFromDataStore("API_KEY").first()
        val site = s.getFromDataStore("SITE").first()

        val jsonString = """
    {"date":"${getCurrentTimeInISO8601()}","rating":${rating.value},"site":"$site"}""".trimIndent()

        val json = Json.parseToJsonElement(jsonString)

        ExternalApi.retrofitService.postDailyReview(
            json,
            "Token $token"
        )
    } catch(e : Exception) {
        Log.d("TAG", "postReview: $e")
    }
}