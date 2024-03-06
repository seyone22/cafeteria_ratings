package com.seyone22.cafeteriaRatings.data

import android.content.Context

interface AppContainer {
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepositories]
     */
}