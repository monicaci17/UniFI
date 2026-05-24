package com.example.unifi.ui.screens.herramientas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.viewmodel.TarjetasMemoriaViewModel
import kotlinx.coroutines.delay

data class Herramienta(
    val titulo: String,
    val descripcion: String
)

@Composable
fun HerramientasScreen(
    tarjetasViewModel: TarjetasMemoriaViewModel = viewModel()
) {
    var tiempoRestante by remember { mutableIntStateOf(25 * 60) }
    var corriendo by remember { mutableStateOf(false) }

    var mostrarTarjetas by remember { mutableStateOf(false) }
    var nombreTema by remember { mutableStateOf("") }
    var temaSeleccionado by remember { mutableStateOf(-1) }

    var pregunta by remember { mutableStateOf("") }
    var respuesta by remember { mutableStateOf("") }

    var mostrarRespuesta by remember { mutableStateOf(false) }
    var indiceTarjeta by remember { mutableStateOf(0) }

    LaunchedEffect(corriendo) {
        while (corriendo && tiempoRestante > 0) {
            delay(1000)
            tiempoRestante--
        }

        if (tiempoRestante == 0) {
            corriendo = false
        }
    }

    val minutos = tiempoRestante / 60
    val segundos = tiempoRestante % 60

    val herramientas = listOf(
        Herramienta("Repaso activo", "Intenta responder preguntas sin ver tus apuntes."),
        Herramienta("Método Feynman", "Explica el tema como si se lo enseñaras a alguien más."),
        Herramienta("Mapas mentales", "Organiza ideas y relaciones de forma visual."),
        Herramienta("Tarjetas de memoria", "Crea temas, guarda preguntas y repasa respuestas.")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = "HERRAMIENTAS DE ESTUDIO",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Pomodoro",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "%02d:%02d".format(minutos, segundos),
                            fontSize = 60.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Trabaja durante 25 minutos enfocado y descansa 5 minutos. Este método ayuda a mejorar la concentración y evitar el agotamiento.",
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Button(
                                onClick = { corriendo = true },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Iniciar", maxLines = 1)
                            }

                            Button(
                                onClick = { corriendo = false },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Pausar", maxLines = 1)
                            }

                            Button(
                                onClick = {
                                    corriendo = false
                                    tiempoRestante = 25 * 60
                                },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier.weight(1.3f)
                            ) {
                                Text("Reiniciar", maxLines = 1)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            items(herramientas) { herramienta ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    onClick = {
                        if (herramienta.titulo == "Tarjetas de memoria") {
                            mostrarTarjetas = !mostrarTarjetas
                        }
                    }
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = herramienta.titulo,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(text = herramienta.descripcion)
                    }
                }
            }

            item {
                if (mostrarTarjetas) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Tarjetas de memoria",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Crear tema")

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = nombreTema,
                            onValueChange = { nombreTema = it },
                            label = { Text("Nombre del tema") },
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = {
                                tarjetasViewModel.agregarTema(nombreTema)
                                nombreTema = ""
                                temaSeleccionado = -1
                                indiceTarjeta = 0
                                mostrarRespuesta = false
                            }
                        ) {
                            Text("Agregar")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Temas")

                    Spacer(modifier = Modifier.height(6.dp))

                    tarjetasViewModel.temas.forEachIndexed { index, tema ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    temaSeleccionado = index
                                    indiceTarjeta = 0
                                    mostrarRespuesta = false
                                    tarjetasViewModel.cargarTarjetas(index)
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(tema.nombre)
                            }

                            Button(
                                onClick = {
                                    tarjetasViewModel.eliminarTema(index)

                                    if (temaSeleccionado == index) {
                                        temaSeleccionado = -1
                                        indiceTarjeta = 0
                                        mostrarRespuesta = false
                                    }
                                }
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }

                    if (
                        temaSeleccionado != -1 &&
                        temaSeleccionado < tarjetasViewModel.temas.size
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Tema seleccionado: ${tarjetasViewModel.temas[temaSeleccionado].nombre}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = pregunta,
                            onValueChange = { pregunta = it },
                            label = { Text("Pregunta") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = respuesta,
                            onValueChange = { respuesta = it },
                            label = { Text("Respuesta") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                tarjetasViewModel.agregarTarjeta(
                                    temaSeleccionado,
                                    pregunta,
                                    respuesta
                                )
                                pregunta = ""
                                respuesta = ""
                                indiceTarjeta = 0
                                mostrarRespuesta = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Guardar tarjeta")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        val tarjetas = tarjetasViewModel.temas[temaSeleccionado].tarjetas

                        if (tarjetas.isNotEmpty()) {
                            if (indiceTarjeta >= tarjetas.size) {
                                indiceTarjeta = 0
                            }

                            val tarjeta = tarjetas[indiceTarjeta]

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(6.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Tarjeta ${indiceTarjeta + 1} de ${tarjetas.size}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Text(
                                        text = "Pregunta:",
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = tarjeta.pregunta,
                                        textAlign = TextAlign.Center
                                    )

                                    if (mostrarRespuesta) {
                                        Spacer(modifier = Modifier.height(12.dp))

                                        Text(
                                            text = "Respuesta:",
                                            style = MaterialTheme.typography.titleMedium
                                        )

                                        Text(
                                            text = tarjeta.respuesta,
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {

                                        Button(
                                            onClick = {
                                                mostrarRespuesta = !mostrarRespuesta
                                            }
                                        ) {
                                            Text(if (mostrarRespuesta) "Ocultar" else "Mostrar")
                                        }

                                        Button(
                                            onClick = {
                                                indiceTarjeta = (indiceTarjeta + 1) % tarjetas.size
                                                mostrarRespuesta = false
                                            }
                                        ) {
                                            Text("Siguiente")
                                        }

                                        Button(
                                            onClick = {

                                                tarjetasViewModel.eliminarTarjeta(
                                                    temaSeleccionado,
                                                    indiceTarjeta
                                                )

                                                mostrarRespuesta = false

                                                if (indiceTarjeta > 0) {
                                                    indiceTarjeta--
                                                }
                                            }
                                        ) {
                                            Text("Eliminar")
                                        }
                                    }
                                }
                            }
                        } else {
                            Text("Aún no hay tarjetas en este tema.")
                        }
                    }
                }
            }
        }
    }
}