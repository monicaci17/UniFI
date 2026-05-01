package com.example.unifi.ui.screens.homemenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.unifi.R

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

        Spacer(modifier = Modifier.height(16.dp))

        // 🔥 BIENVENIDA CON IMAGEN
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(horizontal = 12.dp)
        ) {

            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.cu),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )

            // Capa oscura para contraste
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = 0.35f))
            )

            // Texto encima
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Bienvenid@ a UNIFI",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "¡Comienza a organizarte!",
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

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
                    navController.navigate(Routes.Perfil.route)
                }
                MenuButton("Calendario", Icons.Default.DateRange, Modifier.weight(1f)) {
                    navController.navigate(Routes.Calendario.route)
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                MenuButton(
                    "Motivación",
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

        Spacer(modifier = Modifier.height(20.dp))

        // Botón de configuración
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Routes.Config2.route)
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