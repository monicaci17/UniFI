package com.example.unifi.ui.screens.herramientas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.data.model.IdeaSinoptica
import com.example.unifi.data.model.SubideaSinoptica
import com.example.unifi.viewmodel.CuadroSinopticoViewModel
import com.example.unifi.viewmodel.TarjetasMemoriaViewModel
import kotlinx.coroutines.delay

@Composable
fun HerramientasScreen(
    tarjetasViewModel: TarjetasMemoriaViewModel = viewModel(),
    cuadroViewModel: CuadroSinopticoViewModel = viewModel()
) {
    var tiempoRestante by remember { mutableIntStateOf(25 * 60) }
    var corriendo by remember { mutableStateOf(false) }

    var mostrarTarjetas by remember { mutableStateOf(false) }
    var mostrarCuadros by remember { mutableStateOf(false) }

    var nombreTema by remember { mutableStateOf("") }
    var temaSeleccionado by remember { mutableStateOf(-1) }
    var pregunta by remember { mutableStateOf("") }
    var respuesta by remember { mutableStateOf("") }
    var mostrarRespuesta by remember { mutableStateOf(false) }
    var indiceTarjeta by remember { mutableStateOf(0) }

    var tituloCuadro by remember { mutableStateOf("") }
    var conceptoGeneral by remember { mutableStateOf("") }
    var ideaPrincipal by remember { mutableStateOf("") }
    var tituloSubidea by remember { mutableStateOf("") }
    var detallesTexto by remember { mutableStateOf("") }

    val subideasTemp = remember { mutableStateListOf<SubideaSinoptica>() }
    val ideasTemp = remember { mutableStateListOf<IdeaSinoptica>() }

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
                .padding(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Pomodoro", style = MaterialTheme.typography.titleLarge)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "%02d:%02d".format(minutos, segundos),
                            fontSize = 60.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Trabaja durante 25 minutos enfocado y descansa 5 minutos.",
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Button(
                                onClick = { corriendo = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Iniciar", maxLines = 1)
                            }

                            Button(
                                onClick = { corriendo = false },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Pausar", maxLines = 1)
                            }

                            Button(
                                onClick = {
                                    corriendo = false
                                    tiempoRestante = 25 * 60
                                },
                                modifier = Modifier.weight(1.2f)
                            ) {
                                Text("Reiniciar", maxLines = 1)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp),
                    onClick = { mostrarTarjetas = !mostrarTarjetas }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Tarjetas de memoria",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text("Crea temas, guarda preguntas y repasa respuestas.")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp),
                    onClick = { mostrarCuadros = !mostrarCuadros }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Cuadros sinópticos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text("Organiza un tema en concepto general, ideas, subideas y detalles.")
                    }
                }

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

                        val tarjetas =
                            tarjetasViewModel.temas[temaSeleccionado].tarjetas

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

                                    Text("Pregunta:", style = MaterialTheme.typography.titleMedium)

                                    Text(
                                        text = tarjeta.pregunta,
                                        textAlign = TextAlign.Center
                                    )

                                    if (mostrarRespuesta) {
                                        Spacer(modifier = Modifier.height(12.dp))

                                        Text(
                                            "Respuesta:",
                                            style = MaterialTheme.typography.titleMedium
                                        )

                                        Text(
                                            text = tarjeta.respuesta,
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = {
                                                mostrarRespuesta = !mostrarRespuesta
                                            }
                                        ) {
                                            Text(if (mostrarRespuesta) "Ocultar" else "Mostrar")
                                        }

                                        Button(
                                            onClick = {
                                                indiceTarjeta =
                                                    (indiceTarjeta + 1) % tarjetas.size
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

                if (mostrarCuadros) {
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Cuadros sinópticos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = tituloCuadro,
                        onValueChange = { tituloCuadro = it },
                        label = { Text("Título del cuadro") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = conceptoGeneral,
                        onValueChange = { conceptoGeneral = it },
                        label = { Text("Concepto general") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Construir idea principal",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = ideaPrincipal,
                                onValueChange = { ideaPrincipal = it },
                                label = { Text("Idea principal") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Subideas de esta idea",
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = tituloSubidea,
                                onValueChange = { tituloSubidea = it },
                                label = { Text("Subidea") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = detallesTexto,
                                onValueChange = { detallesTexto = it },
                                label = { Text("Detalles separados por coma") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    val detalles =
                                        detallesTexto.split(",")
                                            .map { it.trim() }
                                            .filter { it.isNotBlank() }

                                    if (tituloSubidea.isNotBlank()) {
                                        subideasTemp.add(
                                            SubideaSinoptica(
                                                titulo = tituloSubidea,
                                                detalles = detalles
                                            )
                                        )

                                        tituloSubidea = ""
                                        detallesTexto = ""
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Agregar subidea")
                            }

                            if (subideasTemp.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(12.dp))

                                subideasTemp.forEachIndexed { index, subidea ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text(
                                                text = "${index + 1}. ${subidea.titulo}",
                                                fontWeight = FontWeight.Bold
                                            )

                                            subidea.detalles.forEach { detalle ->
                                                Text(
                                                    text = "   • $detalle",
                                                    fontSize = 14.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    if (
                                        ideaPrincipal.isNotBlank() &&
                                        subideasTemp.isNotEmpty()
                                    ) {
                                        ideasTemp.add(
                                            IdeaSinoptica(
                                                titulo = ideaPrincipal,
                                                subideas = subideasTemp.toList()
                                            )
                                        )

                                        ideaPrincipal = ""
                                        tituloSubidea = ""
                                        detallesTexto = ""
                                        subideasTemp.clear()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Agregar idea principal al cuadro")
                            }
                        }
                    }

                    if (ideasTemp.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Vista previa del cuadro",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ideasTemp.forEachIndexed { indexIdea, idea ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "${indexIdea + 1}. ${idea.titulo}",
                                        fontWeight = FontWeight.Bold
                                    )

                                    idea.subideas.forEachIndexed { indexSubidea, subidea ->
                                        Text(
                                            text = "   ${indexIdea + 1}.${indexSubidea + 1} ${subidea.titulo}",
                                            fontWeight = FontWeight.SemiBold
                                        )

                                        subidea.detalles.forEachIndexed { indexDetalle, detalle ->
                                            Text(
                                                text = "      ${indexIdea + 1}.${indexSubidea + 1}.${indexDetalle + 1} $detalle",
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            cuadroViewModel.agregarCuadro(
                                titulo = tituloCuadro,
                                conceptoGeneral = conceptoGeneral,
                                ideas = ideasTemp.toList()
                            )

                            tituloCuadro = ""
                            conceptoGeneral = ""
                            ideaPrincipal = ""
                            tituloSubidea = ""
                            detallesTexto = ""
                            subideasTemp.clear()
                            ideasTemp.clear()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar cuadro sinóptico")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Mis cuadros",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    cuadroViewModel.cuadros.forEachIndexed { index, cuadro ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = cuadro.titulo,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Concepto general:",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(text = cuadro.conceptoGeneral)

                                Spacer(modifier = Modifier.height(12.dp))

                                cuadro.ideas.forEachIndexed { indexIdea, idea ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 6.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Text(
                                                text = "${indexIdea + 1}. ${idea.titulo}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )

                                            Spacer(modifier = Modifier.height(6.dp))

                                            idea.subideas.forEachIndexed { indexSubidea, subidea ->
                                                Text(
                                                    text = "   ${indexIdea + 1}.${indexSubidea + 1} ${subidea.titulo}",
                                                    fontWeight = FontWeight.SemiBold
                                                )

                                                subidea.detalles.forEachIndexed { indexDetalle, detalle ->
                                                    Text(
                                                        text = "      ${indexIdea + 1}.${indexSubidea + 1}.${indexDetalle + 1} $detalle",
                                                        fontSize = 14.sp
                                                    )
                                                }

                                                Spacer(modifier = Modifier.height(4.dp))
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = {
                                        cuadroViewModel.eliminarCuadro(index)
                                    }
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
}