package com.seyone22.cafeteriaRatings.data.externalApi

class OfflineExternalApiRepository(private val externalApiRepository : ExternalApiRepository) :
    ExternalApiRepository {
    override fun postDailyReview(): Review = externalApiRepository.postDailyReview()
}