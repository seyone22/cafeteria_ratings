package com.seyone22.cafeteriaRatings

import android.app.Application
import com.seyone22.cafeteriaRatings.data.AppContainer
import com.seyone22.cafeteriaRatings.data.AppDataContainer

class CafeteriaRatingsApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = AppDataContainer(this)
    }
}