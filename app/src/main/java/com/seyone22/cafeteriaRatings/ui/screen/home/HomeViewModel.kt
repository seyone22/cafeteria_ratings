package com.seyone22.cafeteriaRatings.ui.screen.home

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.data.SecureDataStoreManager
import com.seyone22.cafeteriaRatings.data.externalApi.ExternalApi
import com.seyone22.cafeteriaRatings.getCurrentTimeInISO8601
import com.seyone22.cafeteriaRatings.model.Dissatisfied
import com.seyone22.cafeteriaRatings.model.Neutral
import com.seyone22.cafeteriaRatings.model.Rating
import com.seyone22.cafeteriaRatings.model.RatingPeriod
import com.seyone22.cafeteriaRatings.model.RatingsStore
import com.seyone22.cafeteriaRatings.model.Review
import com.seyone22.cafeteriaRatings.model.Satisfied
import com.seyone22.cafeteriaRatings.model.add
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

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
    ) {
        currentSessionRatings.value.add(rating)
        dataStoreManager.saveRatingToDataStore(currentSessionRatings.value)

        // Push rating to server
        postReview(rating, context)
    }
}

suspend fun postReview(
    rating : Rating,
    context : Context
) {
    val s = SecureDataStoreManager(context)
    val d = DataStoreManager(context)

    try {
        ExternalApi.retrofitService.postDailyReview(
            Review(
                timestamp = getCurrentTimeInISO8601(),
                rating = rating.value.toFloat(),
                site = d.getFromDataStore("SITE").first().toString()
            ),
            "Token ${
                s.getFromDataStore("API_KEY").first()
            }")
    } catch(e : Exception) {
        Log.d("TAG", "postReview: $e")
    }
}