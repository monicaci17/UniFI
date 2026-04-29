package com.example.unifi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.unifi.ui.screens.notas.NotasScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Notas.route
    ) {
        composable(Routes.Notas.route) {
            NotasScreen()
        }
    }
}