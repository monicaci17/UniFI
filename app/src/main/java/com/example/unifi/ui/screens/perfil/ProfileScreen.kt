package com.example.unifi.ui.screens.perfil

import android.R.attr.fontWeight
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unifi.viewmodel.UserViewModel

@Composable
fun ProfileScreen(viewModel: UserViewModel) {

    val user = viewModel.getUser()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // 🔹 Encabezado fijo
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            tonalElevation = 4.dp
        ) {
            Text(
                text = "U N I F I",
                modifier = Modifier.padding(16.dp),
                fontSize = 30.sp,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }

        // 🔹 Contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Perfil",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    if (user != null) {

                        Text(
                            text = "Nombre",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = user.nombre,

                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Correo",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(user.correo, style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Usuario", style = MaterialTheme.typography.labelMedium,
                                fontSize = 17.sp,
                            fontWeight = FontWeight.Bold)
                        Text(user.usuario, style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(12.dp))


                    } else {
                        Text(
                            "No hay usuario registrado",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}