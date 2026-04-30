package com.example.unifi.ui.screens.homemenu

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HomeMenuScreen(navController: NavHostController) {

    Column(modifier = Modifier.fillMaxSize()) {

        // Encabezado fijo
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF3A7BD5) // verde, por ejemplo
        ) {
            Text(
                text = "U N I F I",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
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
                MenuButton("Perfil", Icons.Default.Person, Modifier.weight(1f)) {

                }
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

                }
                MenuButton(
                    "Herramientas\nde estudio",
                    Icons.Default.CollectionsBookmark,
                    Modifier.weight(1f)
                ) {

                }
                MenuButton("Metas", Icons.Default.Flag, Modifier.weight(1f)) {

                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                MenuButton("Agregar\ntarea", Icons.Default.AddTask, Modifier.weight(1f)) {

                }
                MenuButton("Horario", Icons.Default.Schedule, Modifier.weight(1f)) {
                    navController.navigate(Routes.Horario.route)
                }
                MenuButton("Foro", Icons.Default.Forum, Modifier.weight(1f)) {

                }
            }


        }
        Spacer(modifier = Modifier.height(30.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF3A7BD5)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configuración",
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(30.dp))

                Text(
                    text = "Configuración",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 20.sp,
                    color = Color.White
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
                containerColor = Color(0xFFA7C7E7) // tu azul suave
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
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = text,
                textAlign = TextAlign.Center
            )
        }
    }
}