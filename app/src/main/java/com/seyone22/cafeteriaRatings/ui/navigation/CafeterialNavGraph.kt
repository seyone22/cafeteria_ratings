package com.seyone22.cafeteriaRatings.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.ui.screen.home.HomeDestination
import com.seyone22.cafeteriaRatings.ui.screen.settings.SettingsDestination
import com.seyone22.cafeteriaRatings.ui.screen.settings.SettingsDetailDestination
import com.seyone22.cafeteriaRatings.ui.screen.settings.SettingsDetailScreen
import com.seyone22.cafeteriaRatings.ui.screen.settings.SettingsScreen
import com.seyone22.cafeteriaRatings.ui.screen.home.HomeScreen

/**
 * Provides Navigation graph for the application.
 */

@Composable
fun CafeteriaNavHost(
    navController: NavHostController,
    windowSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    innerPadding : PaddingValues,
    dataStoreManager: DataStoreManager,
    goFullscreen : () -> Unit
) {
    NavHost(
        modifier = modifier.padding(innerPadding),
        navController = navController,
        startDestination = HomeDestination.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        // Routes to main Navbar destinations
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToScreen = { screen -> navController.navigate(screen) },
                dataStoreManager = dataStoreManager,
                modifier = modifier,
                goFullscreen = goFullscreen
            )
        }
        // Routes to settings screen
        composable(route = SettingsDestination.route) {
            SettingsScreen(
                navigateToScreen = { screen -> navController.navigate(screen) },
                dataStoreManager = dataStoreManager,
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(route = SettingsDetailDestination.route + "/{setting}",
            arguments = listOf(navArgument("setting") { type = NavType.StringType })
        ) {
            SettingsDetailScreen(
                navigateToScreen = { screen -> navController.navigate(screen) },
                dataStoreManager = dataStoreManager,
                navigateBack = { navController.popBackStack() },
                backStackEntry = it.arguments?.getString("setting") ?: "-1"
            )
        }
    }
}
