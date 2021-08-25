package com.example.f1.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.navigation
import com.example.f1.ui.MainDestinations.TELEMETRY_ID_KEY
import com.example.f1.ui.navigation.NavigationSections
import com.example.f1.ui.navigation.NewTelemetrySession
import com.example.f1.ui.navigation.addHomeGraph
import com.example.f1.ui.telemetry.NewSession
import com.example.f1.ui.telemetry.SessionDetail

object MainDestinations {
    const val HOME_ROUTE = "home"
    // TODO: Sub route? telemetry_session/new
    const val NEW_TELEMETRY_SESSION_ROUTE = "new_telemetry_session"
    const val TELEMETRY_SESSION_ROUTE = "telemetry_session"
    const val TELEMETRY_ID_KEY = "telemetrySessionId"
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
            addHomeGraph(
                // TODO: Pass onclick lambda instead of navcontroller
                navController = navController,
                onClickTelemetrySession = { telemetrySessionId, from ->
                    if (from.lifecycleIsResumed()) {
                        navController.navigate("${MainDestinations.TELEMETRY_SESSION_ROUTE}/$telemetrySessionId")
                    }
                },
                modifier = modifier
            )
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

        composable(
            "${MainDestinations.TELEMETRY_SESSION_ROUTE}/{$TELEMETRY_ID_KEY}",
            arguments = listOf(navArgument(TELEMETRY_ID_KEY) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val telemetrySessionId = arguments.getLong(TELEMETRY_ID_KEY)
            SessionDetail(
                telemetrySessionId = telemetrySessionId,
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