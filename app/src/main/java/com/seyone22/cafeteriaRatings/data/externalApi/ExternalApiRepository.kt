package com.seyone22.cafeteriaRatings.data.externalApi

import com.seyone22.cafeteriaRatings.model.Review


interface ExternalApiRepository {
    fun postDailyReview(): Review
}