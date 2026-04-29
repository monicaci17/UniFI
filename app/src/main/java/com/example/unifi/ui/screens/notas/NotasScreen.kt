package com.example.unifi.ui.screens.notas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.viewmodel.NotaViewModel

@Composable
fun NotasScreen(vm: NotaViewModel = viewModel()) {

    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("UNIFI - Notas", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") }
        )

        OutlinedTextField(
            value = contenido,
            onValueChange = { contenido = it },
            label = { Text("Contenido") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (titulo.isNotBlank() && contenido.isNotBlank()) {
                vm.agregarNota(titulo, contenido)
                titulo = ""
                contenido = ""
            }
        }) {
            Text("Agregar Nota")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(vm.notas) { nota ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(nota.titulo, style = MaterialTheme.typography.titleMedium)
                        Text(nota.contenido)

                        Button(onClick = {
                            vm.eliminarNota(nota)
                        }) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}