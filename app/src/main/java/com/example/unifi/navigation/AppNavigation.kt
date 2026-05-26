package com.example.unifi.navigation

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.activity
import androidx.navigation.compose.*
import com.example.unifi.ui.screens.calendario.CalendarioScreen
import com.example.unifi.ui.screens.config2.SettingsScreen
import com.example.unifi.ui.screens.configuracion.ConfiguracionScreen
import com.example.unifi.ui.screens.foro.ForoScreen
import com.example.unifi.ui.screens.herramientas.HerramientasScreen
import com.example.unifi.ui.screens.homemenu.HomeMenuScreen
import com.example.unifi.ui.screens.horario.HorarioScreen
import com.example.unifi.ui.screens.metas.MetasScreen
import com.example.unifi.ui.screens.notas.NotasScreen
import com.example.unifi.ui.screens.perfil.PerfilScreen
import com.example.unifi.ui.screens.registro.RegisterScreen
import com.example.unifi.ui.screens.relajacion.RelajacionScreen
import com.example.unifi.ui.screens.tareas.TareasScreen
import com.example.unifi.viewmodel.AuthViewModel
import com.example.unifi.viewmodel.UserViewModel
import com.example.unifi.ui.screens.auth.LoginScreen
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(viewModel: UserViewModel) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.uiState.collectAsState()
    val startRoute = if (authState.isLoggedIn) Routes.HomeMenu.route else Routes.Login.route

    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable(Routes.Login.route) {
            LoginScreen(authViewModel = authViewModel) {
                navController.navigate(Routes.HomeMenu.route) {
                    popUpTo(Routes.Login.route) { inclusive = true }
                }
            }
        }

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
        composable(Routes.Registro.route) {
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    navController.navigate(Routes.HomeMenu.route)
                }
            )
        }
        composable(Routes.Perfil.route) {
            PerfilScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Routes.Config2.route) {
            val activity = LocalContext.current as Activity
            SettingsScreen(viewModel = viewModel, activity = activity)
        }
    }
}