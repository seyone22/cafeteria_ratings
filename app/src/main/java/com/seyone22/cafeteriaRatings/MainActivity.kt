package com.seyone22.cafeteriaRatings

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.data.SecureDataStoreManager
import com.seyone22.cafeteriaRatings.data.externalApi.ExternalApi
import com.seyone22.cafeteriaRatings.model.RatingsStore
import com.seyone22.cafeteriaRatings.model.Review
import com.seyone22.cafeteriaRatings.ui.theme.CafeteriaRatingsTheme
import kotlinx.coroutines.flow.first
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CafeteriaRatingsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CafeteriaRatingsApp(
                        windowWidthSizeClass = calculateWindowSizeClass(this).widthSizeClass
                    )
                }
            }
        }
    }
}


class PostEmailWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val context = applicationContext
        val dataStoreManager = DataStoreManager(context)
        val secureDataStoreManager = SecureDataStoreManager(context)

        val sum = dataStoreManager.getFromDataStore("RATING_SUM").first().toString().toInt()
        val count = dataStoreManager.getFromDataStore("RATING_COUNT").first().toString().toInt()
        val token = secureDataStoreManager.getFromDataStore("API_KEY")
        val allowAuto = secureDataStoreManager.getFromDataStore("ALLOW_AUTO").toString().toBoolean()

        return try {
            val response = ExternalApi.retrofitService.postDailyReview(
                Review(
                    timestamp = getCurrentTimeInISO8601(),
                    rating = (sum.toFloat() / count.toFloat()),
                    site = "count.toFloat()"
                ), "Token $token"
            )
            if (response.status == "success") {
                dataStoreManager.saveRatingToDataStore(RatingsStore(0,0f))
                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}

fun getCurrentTimeInISO8601(): String {
    val currentTime = ZonedDateTime.now()
    val formatter = DateTimeFormatter.ISO_INSTANT
    return currentTime.format(formatter)
}

fun calculateInitialDelay(desiredHour : Int, desiredMinute : Int): Long {
    val now = Calendar.getInstance()
    val desiredTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, desiredHour)
        set(Calendar.MINUTE, desiredMinute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    return if (now.after(desiredTime)) {
        // If the desired time has passed today, schedule for the next day
        desiredTime.add(Calendar.DAY_OF_MONTH, 1)
        desiredTime.timeInMillis - now.timeInMillis
    } else {
        // If the desired time is in the future today, schedule for today
        desiredTime.timeInMillis - now.timeInMillis
    }
}