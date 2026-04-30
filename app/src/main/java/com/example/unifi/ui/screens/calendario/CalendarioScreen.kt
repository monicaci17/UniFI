package com.example.unifi.ui.screens.calendario

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.viewmodel.CalendarioViewModel
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarioScreen(vm: CalendarioViewModel = viewModel()) {

    var nuevoEvento by remember { mutableStateOf("") }

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
                text = "CALENDARIO",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center

            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {

                Spacer(modifier = Modifier.height(10.dp))

                // Fecha actual
                Text(
                    text = "Hoy: ${vm.fechaHoy.dayOfMonth} - ${vm.fechaHoy.monthValue} - ${vm.fechaHoy.year}",
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Mes
                Text(
                    text = "${vm.nombreMes()} ${vm.añoActual}",
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Navegación
                Row {
                    Button(onClick = { vm.mesAnterior() }) { Text("<") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { vm.siguienteMes() }) { Text(">") }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Calendario
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.height(260.dp)
                ) {
                    items(vm.obtenerDiasDelMes()) { index ->

                        val dia = index + 1

                        val fecha = "${vm.añoActual}-${
                            vm.mesActual.toString().padStart(2, '0')
                        }-${dia.toString().padStart(2, '0')}"

                        val esHoy = dia == vm.fechaHoy.dayOfMonth &&
                                vm.mesActual == vm.fechaHoy.monthValue &&
                                vm.añoActual == vm.fechaHoy.year

                        val seleccionado = fecha == vm.fechaSeleccionada

                        val colorFondo = when {
                            seleccionado -> MaterialTheme.colorScheme.primary
                            esHoy -> MaterialTheme.colorScheme.secondary
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val colorTexto = when {
                            seleccionado -> MaterialTheme.colorScheme.onPrimary
                            esHoy -> MaterialTheme.colorScheme.onSecondary
                            else -> MaterialTheme.colorScheme.onSurface
                        }

                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable { vm.seleccionarDia(dia) },
                            colors = CardDefaults.cardColors(
                                containerColor = colorFondo
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$dia",
                                    color = colorTexto
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Fecha seleccionada
                Text(
                    text = "Seleccionado: ${vm.fechaSeleccionada}",
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Input
                OutlinedTextField(
                    value = nuevoEvento,
                    onValueChange = { nuevoEvento = it },
                    label = { Text("Nuevo evento") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (nuevoEvento.isNotBlank()) {
                            vm.agregarEvento(nuevoEvento)
                            nuevoEvento = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar evento")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Eventos
                Text(
                    "Eventos del día",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                vm.eventos.forEach {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(
                            text = it.titulo,
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}