package com.example.unifi.ui.screens.foro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.viewmodel.ForoViewModel

@Composable
fun ForoScreen(
    foroViewModel: ForoViewModel = viewModel()
) {
    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }
    var respuesta by remember { mutableStateOf("") }

    var postSeleccionado by remember { mutableStateOf(-1) }

    if (postSeleccionado == -1) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "Foro Dudas",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título de la publicación") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = contenido,
                    onValueChange = { contenido = it },
                    label = { Text("Contenido") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        foroViewModel.agregarPost(titulo, contenido)
                        titulo = ""
                        contenido = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Publicar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Publicaciones",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(foroViewModel.posts) { index, post ->
                Card(
                    onClick = {
                        postSeleccionado = index
                        respuesta = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Text(
                            text = post.titulo,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = post.contenido,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${post.respuestas.size} respuestas",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    } else {
        val post = foroViewModel.posts[postSeleccionado]

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Button(
                    onClick = {
                        postSeleccionado = -1
                        respuesta = ""
                    }
                ) {
                    Text("Volver")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = post.titulo,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = post.contenido,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Respuestas",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            items(post.respuestas.size) { i ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = post.respuestas[i].texto,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = respuesta,
                    onValueChange = { respuesta = it },
                    label = { Text("Escribe una respuesta") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        foroViewModel.agregarRespuesta(postSeleccionado, respuesta)
                        respuesta = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Responder")
                }
            }
        }
    }
}