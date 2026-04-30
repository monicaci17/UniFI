package com.example.unifi.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.unifi.ui.screens.calendario.CalendarioScreen
import com.example.unifi.ui.screens.configuracion.ConfiguracionScreen
import com.example.unifi.ui.screens.foro.ForoScreen
import com.example.unifi.ui.screens.herramientas.HerramientasScreen
import com.example.unifi.ui.screens.homemenu.HomeMenuScreen
import com.example.unifi.ui.screens.horario.HorarioScreen
import com.example.unifi.ui.screens.metas.MetasScreen
import com.example.unifi.ui.screens.notas.NotasScreen
import com.example.unifi.ui.screens.relajacion.RelajacionScreen
import com.example.unifi.ui.screens.tareas.TareasScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HomeMenu.route
    ) {

        composable(Routes.HomeMenu.route) {
            HomeMenuScreen(navController)
        }
        composable(Routes.Notas.route) {
            NotasScreen()
        }
        composable(Routes.Calendario.route) {
            CalendarioScreen()
        }
        composable(Routes.Horario.route) {
            HorarioScreen()
        }
        composable(Routes.Relajacion.route) {
            RelajacionScreen()
        }

        composable(Routes.Herramientas.route) {
            HerramientasScreen()
        }

        composable(Routes.Foro.route) {
            ForoScreen()
        }

        composable(Routes.Configuracion.route) {
            ConfiguracionScreen()
        }

        composable(Routes.Tareas.route) {
            TareasScreen()
        }
        composable(Routes.Metas.route) {
            MetasScreen()
        }
    }
}