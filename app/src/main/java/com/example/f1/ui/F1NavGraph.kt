package com.example.f1.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.f1.ui.navigation.NavigationSections
import com.example.f1.ui.navigation.NewTelemetrySession
import com.example.f1.ui.navigation.addHomeGraph
import com.example.f1.ui.telemetry.NewSession

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val NEW_TELEMETRY_SESSION_ROUTE = "new_telemetry_session"
}

@Composable
fun F1NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        navigation(
            route = startDestination,
            startDestination = NavigationSections.TELEMETRY.route
        ) {
            addHomeGraph(navController = navController, modifier = modifier)
        }

        composable(
            MainDestinations.NEW_TELEMETRY_SESSION_ROUTE
        ) { backStackEntry ->
            NewSession(
                upPress = {
                    navController.navigateUp()
                }
            )
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED