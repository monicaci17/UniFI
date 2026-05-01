package com.example.unifi.ui.screens.notas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.viewmodel.NotaViewModel

@Composable
fun NotasScreen(vm: NotaViewModel = viewModel()) {

    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {

        // 🔹 Encabezado fijo
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            tonalElevation = 4.dp
        ) {
            Text(
                text = "N O T A S",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {


            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Formulario en Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Título") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = contenido,
                        onValueChange = { contenido = it },
                        label = { Text("Contenido") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (titulo.isNotBlank() && contenido.isNotBlank()) {
                                vm.agregarNota(titulo, contenido)
                                titulo = ""
                                contenido = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Agregar nota")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Lista de notas
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(vm.notas) { nota ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = nota.titulo,
                                style = MaterialTheme.typography.titleSmall
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = nota.contenido,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = { vm.eliminarNota(nota) },
                                modifier = Modifier.align(Alignment.End),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}