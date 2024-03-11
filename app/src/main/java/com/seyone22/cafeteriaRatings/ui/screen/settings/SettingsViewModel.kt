package com.seyone22.cafeteriaRatings.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import kotlinx.coroutines.launch

class SettingsViewModel() : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}