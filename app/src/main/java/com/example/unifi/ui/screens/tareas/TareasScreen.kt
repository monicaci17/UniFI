package com.example.unifi.ui.screens.tareas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.viewmodel.PendienteViewModel

@Composable
fun TareasScreen(vm: PendienteViewModel = viewModel()) {

    var descripcion by remember { mutableStateOf("") }
    var asignatura by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // 🔵 ENCABEZADO
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = "T A R E A S",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center

            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = asignatura,
            onValueChange = { asignatura = it },
            label = { Text("Asignatura (ej. Mecatrónica)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("¿Qué tienes pendiente?") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (descripcion.isNotBlank() && asignatura.isNotBlank()) {
                    vm.agregarTarea(descripcion, asignatura)
                    descripcion = ""
                    asignatura = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Guardar Tarea en Firebase")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(vm.listaTareas) { tarea ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (tarea.estaTerminado) Color(0xFFF0F0F0) else Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = tarea.estaTerminado,
                            onCheckedChange = { vm.cambiarEstadoTarea(tarea) }
                        )

                        Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                            Text(
                                text = tarea.asignatura.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (tarea.estaTerminado) Color.Gray else Color(0xFF3A7BD5)
                            )
                            Text(
                                text = tarea.descripcion,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    textDecoration = if (tarea.estaTerminado) TextDecoration.LineThrough else TextDecoration.None,
                                    color = if (tarea.estaTerminado) Color.Gray else Color.Black
                                )
                            )
                        }

                        IconButton(onClick = { vm.eliminarTarea(tarea) }) {
                            Text("❌", fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}}