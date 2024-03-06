package com.seyone22.cafeteriaRatings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.ui.screen.HomeScreen

@Composable
fun CafeteriaRatingsApp(
    modifier: Modifier = Modifier
) {
    val dataStoreManager = DataStoreManager(LocalContext.current)

    HomeScreen(modifier = modifier, dataStoreManager = dataStoreManager)
}