package com.dabomstew.pkrandomandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dabomstew.pkrandomandroid.ui.screens.HomeScreen
import com.dabomstew.pkrandomandroid.ui.screens.LogScreen
import com.dabomstew.pkrandomandroid.ui.screens.SettingsScreen
import com.dabomstew.pkrandomandroid.viewmodel.RandomizerViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("settings")
    object Log : Screen("log")
}

@Composable
fun AppNavigation(controllerConnected: Boolean) {
    val navController = rememberNavController()
    val viewModel: RandomizerViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                controllerConnected = controllerConnected,
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToLog = { navController.navigate(Screen.Log.route) }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = viewModel,
                controllerConnected = controllerConnected,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Log.route) {
            LogScreen(
                viewModel = viewModel,
                controllerConnected = controllerConnected,
                onDone = {
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                }
            )
        }
    }
}
