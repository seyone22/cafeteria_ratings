package com.seyone22.cafeteriaRatings.data.externalApi

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.seyone22.cafeteriaRatings.model.Review
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import okhttp3.logging.HttpLoggingInterceptor

private const val BASE_URL_TESTING =
    "http://10.0.2.2:8000/"
private const val BASE_URL =
    "https://survey.etsteas.co.uk/"

private val json = Json { ignoreUnknownKeys = true }


val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface ExternalApiService {
    @POST("/api/reviews")
    suspend fun postDailyReview(
        @Body requestBody: JsonElement,
        @Header("Authorization") authorization: String,
    ) : Review
}

object ExternalApi {
    val retrofitService : ExternalApiService by lazy {
        retrofit.create(ExternalApiService::class.java)
    }
}