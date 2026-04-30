package com.example.unifi.ui.screens.homemenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.unifi.navigation.Routes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HomeMenuScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // Encabezado
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = "U N I F I",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                MenuButton("Notas", Icons.Default.Edit, Modifier.weight(1f)) {
                    navController.navigate(Routes.Notas.route)
                }
                MenuButton("Perfil", Icons.Default.Person, Modifier.weight(1f)) {}
                MenuButton("Calendario", Icons.Default.DateRange, Modifier.weight(1f)) {
                    navController.navigate(Routes.Calendario.route)
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                MenuButton(
                    "Técnicas de\nrelajación",
                    Icons.Default.SelfImprovement,
                    Modifier.weight(1f)
                ) {
                    navController.navigate(Routes.Relajacion.route)
                }
                MenuButton(
                    "Herramientas\nde estudio",
                    Icons.Default.CollectionsBookmark,
                    Modifier.weight(1f)
                ) {
                    navController.navigate(Routes.Herramientas.route)
                }
                MenuButton("Metas", Icons.Default.Flag, Modifier.weight(1f)) {
                    navController.navigate(Routes.Metas.route)
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                MenuButton("Agregar\ntarea", Icons.Default.AddTask, Modifier.weight(1f)) {
                    navController.navigate(Routes.Tareas.route)
                }
                MenuButton("Horario", Icons.Default.Schedule, Modifier.weight(1f)) {
                    navController.navigate(Routes.Horario.route)
                }
                MenuButton("Foro", Icons.Default.Forum, Modifier.weight(1f)) {
                    navController.navigate(Routes.Foro.route)
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Botón de configuración
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Routes.Configuracion.route)
                },
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configuración",
                    tint = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.width(30.dp))

                Text(
                    text = "Configuración",

                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .padding(6.dp)
            .aspectRatio(1f),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}