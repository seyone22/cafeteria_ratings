package com.seyone22.cafeteriaRatings.model

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val timestamp: String,
    val rating: Float,
    val site: Float,
)