package com.seyone22.cafeteriaRatings.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.seyone22.cafeteriaRatings.CafeteriaRatingsApplication
import com.seyone22.cafeteriaRatings.ui.screen.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel()
        }
    }
}

fun CreationExtras.cafeteriaRatingsApplication(): CafeteriaRatingsApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CafeteriaRatingsApplication)