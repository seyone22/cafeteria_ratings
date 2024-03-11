package com.seyone22.cafeteriaRatings.data.externalApi

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

// Define your data class and API service as before
@Serializable
data class Review(
    val date: String,
    val rating_average: Float,
    val rating_count: Float,
    val status: String = ""
)

private const val BASE_URL_TESTING =
    "http://10.0.2.2:8000/"
private const val BASE_URL =
    "http://survey.etsteas.co.uk/"

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface ExternalApiService {
    @POST("/api/reviews/")
    suspend fun postDailyReview(
        @Body review: Review,
        @Header("Authorization") authorization : String
    ) : Review
}

object ExternalApi {
    val retrofitService : ExternalApiService by lazy {
        retrofit.create(ExternalApiService::class.java)
    }
}