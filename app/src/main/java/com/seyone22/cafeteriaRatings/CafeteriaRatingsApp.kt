package com.seyone22.cafeteriaRatings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.seyone22.cafeteriaRatings.data.DataStoreManager
import com.seyone22.cafeteriaRatings.ui.navigation.CafeteriaNavHost
import com.seyone22.cafeteriaRatings.ui.screen.home.HomeDestination
import com.seyone22.cafeteriaRatings.ui.screen.settings.SettingsDestination


@OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)
@Composable
fun CafeteriaRatingsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    windowWidthSizeClass: WindowWidthSizeClass,
    dataStoreManager : DataStoreManager = DataStoreManager(LocalContext.current)
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    var showNav by remember { mutableStateOf(true) }
    val navItems = listOf(HomeDestination, SettingsDestination)

    Row {
        if (showNav) {
            NavigationRail(
                containerColor = Color.Transparent
            ) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = modifier) {
                        navItems.forEachIndexed { index, navItem ->
                            NavigationRailItem(
                                icon = {
                                    Icon(
                                        imageVector = if (selectedItem == index) {
                                            navItem.iconFilled
                                        } else {
                                            navItem.icon
                                        },
                                        contentDescription = navItem.route,
                                    )
                                },
                                label = { Text(text = navItem.route) },
                                selected = selectedItem == index,
                                onClick = {
                                    navController.navigate(navItem.route)
                                    selectedItem = navItem.routeId
                                }
                            )
                        }
                    }
                }
            }
        }

        CafeteriaNavHost(
            navController = navController,
            windowSizeClass = windowWidthSizeClass,
            innerPadding = PaddingValues(),
            dataStoreManager = dataStoreManager,
            goFullscreen = {
                showNav = !showNav
            }
        )
    }

}