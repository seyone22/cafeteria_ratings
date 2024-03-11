package com.seyone22.cafeteriaRatings.data.externalApi


interface ExternalApiRepository {
    fun postDailyReview(): Review
}