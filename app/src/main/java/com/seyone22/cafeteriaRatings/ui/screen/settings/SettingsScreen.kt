package com.seyone22.cafeteriaRatings.ui.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seyone22.cafeteriaRatings.ui.navigation.NavigationDestination
import com.seyone22.cafeteriaRatings.R
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.ui.AppViewModelProvider

object SettingsDestination : NavigationDestination {
    override val route = "Settings"
    override val titleRes = R.string.app_name
    override val routeId = 1
    override val icon = Icons.Outlined.Settings
    override val iconFilled = Icons.Filled.Settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigateToScreen: (screen: String) -> Unit,
    dataStoreManager: DataStoreManager,
    navigateBack: () -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }

                }
            )
        },
    ) { innerPadding ->
        Column(
            Modifier.padding(innerPadding)
        ) {
            ListItem(
                headlineContent = { Text(text = "Connections") },
                supportingContent = { Text(text = "Connect to the Server, Update Frequency") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Upload,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier.clickable { navigateToScreen("SettingsDetail/Connections") }
            )
            ListItem(
                headlineContent = { Text(text = "Appearance") },
                supportingContent = { Text(text = "Home screen customization") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Palette,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier.clickable { navigateToScreen("SettingsDetail/Appearance") }
            )
            ListItem(
                headlineContent = { Text(text = "Data") },
                supportingContent = { Text(text = "View and Delete Saved Data") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.FileCopy,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier.clickable { navigateToScreen("SettingsDetail/Data") }
            )
            ListItem(
                headlineContent = { Text(text = "About") },
                supportingContent = {
                    Text(
                        text = "${stringResource(id = R.string.app_name)} ${
                            stringResource(
                                id = R.string.app_version
                            )
                        }"
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier.clickable {
                    navigateToScreen("SettingsDetail/About")
                }
            )
        }
    }
}