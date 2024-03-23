package com.seyone22.cafeteriaRatings.model

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