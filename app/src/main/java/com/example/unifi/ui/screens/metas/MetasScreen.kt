package com.example.unifi.ui.screens.metas

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
fun MetasScreen(vm: PendienteViewModel = viewModel()) {

    var descripcion by remember { mutableStateOf("") }

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
                text = "M E T A S",
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
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("¿Qué quieres lograr?") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej: Aprobar examen de Mecatrónica") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (descripcion.isNotBlank()) {
                        vm.agregarMeta(descripcion)
                        descripcion = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Establecer Meta")
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(vm.listaMetas) { meta ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (meta.estaTerminado) Color(0xFFE8F5E9) else Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = meta.estaTerminado,
                                onCheckedChange = { vm.cambiarEstadoMeta(meta) }
                            )

                            Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                                Text(
                                    text = if (meta.estaTerminado) "¡LOGRADO!" else "PENDIENTE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (meta.estaTerminado) Color(0xFF4CAF50) else Color.Gray
                                )
                                Text(
                                    text = meta.descripcion,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        textDecoration = if (meta.estaTerminado) TextDecoration.LineThrough else TextDecoration.None,
                                        color = if (meta.estaTerminado) Color.Gray else Color.Black
                                    )
                                )
                            }

                            IconButton(onClick = { vm.eliminarMeta(meta) }) {
                                Text("🗑️", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
