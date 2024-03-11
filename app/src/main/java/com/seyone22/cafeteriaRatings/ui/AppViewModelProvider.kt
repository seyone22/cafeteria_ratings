package com.seyone22.cafeteriaRatings.ui

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.seyone22.cafeteriaRatings.CafeteriaRatingsApplication
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.ui.screen.home.HomeViewModel
import com.seyone22.cafeteriaRatings.ui.screen.settings.SettingsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel()
        }
        initializer {
            SettingsViewModel()
        }
    }
}

fun CreationExtras.cafeteriaRatingsApplication(): CafeteriaRatingsApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CafeteriaRatingsApplication)