package com.seyone22.cafeteriaRatings.model

import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.getCurrentTimeInISO8601

data class RatingPeriod (
    val date : String,
    val rating_average : Float ,
    val rating_count : Int,
    val site : String
)

suspend fun CreateRatingPeriod(rating : List<Rating>, dataStoreManager: DataStoreManager) : RatingPeriod {
    var reviewSum : Float = 0f
    var reviewCount : Int = 0
    rating.forEach { r ->
        reviewCount++
        reviewSum += r.value
    }

    return RatingPeriod(
        date = getCurrentTimeInISO8601(),
        rating_average = (reviewSum / reviewCount),
        rating_count = reviewCount,
        site = dataStoreManager.getFromDataStore("SITE").toString()
    )
}