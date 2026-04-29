package com.example.unifi.navigation

sealed class Routes(val route: String) {
    object Notas : Routes("notas")
}