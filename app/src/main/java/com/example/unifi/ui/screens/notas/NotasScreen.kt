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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.viewmodel.PendienteViewModel

@Composable
fun NotasScreen(vm: PendienteViewModel = viewModel()) {

    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

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
                label = { Text("Título de la nota") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contenido,
                onValueChange = { contenido = it },
                label = { Text("Escribe el contenido aquí...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
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
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Guardar Nota")
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(vm.listaNotas) { nota ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFDEF2FF)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = nota.titulo,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF3A7BD5)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = nota.contenido,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                            }

                            IconButton(onClick = { vm.eliminarNota(nota) }) {
                                Text("🗑️", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
    }}}