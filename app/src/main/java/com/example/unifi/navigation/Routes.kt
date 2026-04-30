package com.example.unifi.navigation

sealed class Routes(val route: String) {
    object Notas : Routes("notas")
    object Calendario : Routes("calendario")
    object Horario : Routes("horario")
    object HomeMenu : Routes("home_menu")
    object Relajacion : Routes("relajacion")
    object Herramientas : Routes("herramientas")
    object Foro : Routes("foro")
}