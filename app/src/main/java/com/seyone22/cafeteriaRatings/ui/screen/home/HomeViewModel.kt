package com.seyone22.cafeteriaRatings.ui.screen.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.model.Dissatisfied
import com.seyone22.cafeteriaRatings.model.Neutral
import com.seyone22.cafeteriaRatings.model.Rating
import com.seyone22.cafeteriaRatings.model.Satisfied
import kotlinx.coroutines.flow.MutableStateFlow

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
    }
}

data class RatingsStore(
    var ratingCount: Int = 0,
    var totalRatingScore: Float = 0f,
    var countSatisfied : Int = 0,
    var countNeutral : Int = 0,
    var countDissatisfied : Int = 0
)

fun RatingsStore.add(rating : Rating) {
    ratingCount++
    totalRatingScore += rating.value

    if (rating == Satisfied) {
        countSatisfied++
    }
    if (rating == Neutral) {
        countNeutral++
    }
    if (rating == Dissatisfied) {
        countDissatisfied++
    }
}