package com.seyone22.cafeteriaRatings.data.externalApi

import com.seyone22.cafeteriaRatings.model.Review

class OfflineExternalApiRepository(private val externalApiRepository : ExternalApiRepository) :
    ExternalApiRepository {
    override fun postDailyReview(): Review = externalApiRepository.postDailyReview()
}