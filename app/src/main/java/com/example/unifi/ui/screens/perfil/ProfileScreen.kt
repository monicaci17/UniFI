package com.example.unifi.ui.screens.perfil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.unifi.R
import com.example.unifi.navigation.Routes
import com.example.unifi.ui.components.AppImage
import com.example.unifi.viewmodel.AuthViewModel

@Composable
fun PerfilScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
) {
    val uiState by authViewModel.uiState.collectAsState()
    val profile = uiState.profile

    var nombre by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }
    var semestre by remember { mutableStateOf("") }
    var meta by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        authViewModel.loadCurrentProfile()
    }

    LaunchedEffect(profile) {
        if (profile != null) {
            nombre = profile.nombre
            carrera = profile.carrera
            semestre = profile.semestre
            meta = profile.meta
        }
    }

    LaunchedEffect(uiState.isLoggedIn) {
        if (!uiState.isLoggedIn) {
            navController.navigate(Routes.Login.route) {
                popUpTo(Routes.HomeMenu.route) { inclusive = true }
            }
        }
    }

    if (!uiState.isLoggedIn) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppImage(
            imageRes = R.drawable.img_unifi_logo,
            description = "Imagen de perfil",
            size = 90.dp
        )

        Text(
            text = "Mi perfil",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = profile?.correo ?: "Correo vinculado a Firebase",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = carrera,
            onValueChange = { carrera = it },
            label = { Text("Carrera") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = semestre,
            onValueChange = { semestre = it },
            label = { Text("Semestre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = meta,
            onValueChange = { meta = it },
            label = { Text("Meta académica") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2
        )

        uiState.message?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        uiState.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { authViewModel.saveProfile(nombre, carrera, semestre, meta) },
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar perfil")
        }

        TextButton(
            onClick = {
                authViewModel.logout()
                navController.navigate(Routes.Login.route) {
                    popUpTo(0)
                }
            }
        ) {
            Text("Cerrar sesión")
        }
    }
}