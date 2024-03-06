package com.seyone22.cafeteriaRatings.ui.screen

import androidx.lifecycle.ViewModel
import com.seyone22.cafeteriaRatings.data.Dissatisfied
import com.seyone22.cafeteriaRatings.data.Neutral
import com.seyone22.cafeteriaRatings.data.Rating
import com.seyone22.cafeteriaRatings.data.Satisfied
import com.seyone22.cafeteriaRatings.data.VeryDissatisfied
import com.seyone22.cafeteriaRatings.data.VerySatisfied
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    val ratingsList = listOf(
        VerySatisfied,
        Satisfied,
        Neutral,
        Dissatisfied,
        VeryDissatisfied
    )

    var currentSessionRatings: MutableStateFlow<RatingsStore> = MutableStateFlow(RatingsStore(0, 0))

    fun recordRating(rating: Rating) {
        currentSessionRatings.value = RatingsStore(
            currentSessionRatings.value.ratingCount + 1,
            currentSessionRatings.value.totalRatingScore + rating.value
        )
    }
}

data class RatingsStore(
    val ratingCount: Int,
    val totalRatingScore: Int
)