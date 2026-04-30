package com.example.unifi.ui.screens.calendario

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.viewmodel.CalendarioViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarioScreen(vm: CalendarioViewModel = viewModel()) {

    var nuevoEvento by remember { mutableStateOf("") }

    Column(    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(30.dp))
        //  Fecha actual
        Text(
            text = "Hoy: ${vm.fechaHoy.dayOfMonth} - ${vm.fechaHoy.monthValue} - ${vm.fechaHoy.year}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mes
        Text(
            text = "${vm.nombreMes()} ${vm.añoActual}",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Navegación
        Row {
            Button(onClick = { vm.mesAnterior() }) { Text("<") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { vm.siguienteMes() }) { Text(">") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📆 Calendario
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.height(260.dp)
        ) {
            items(vm.obtenerDiasDelMes()) { index ->
                val dia = index + 1

                val fecha = "${vm.añoActual}-${vm.mesActual.toString().padStart(2,'0')}-${dia.toString().padStart(2,'0')}"

                val esHoy = dia == vm.fechaHoy.dayOfMonth &&
                        vm.mesActual == vm.fechaHoy.monthValue &&
                        vm.añoActual == vm.fechaHoy.year

                val seleccionado = fecha == vm.fechaSeleccionada

                val colorFondo = when {
                    seleccionado -> Color(0xFF1976D2) // azul
                    esHoy -> Color(0xFF90CAF9) // azul claro
                    else -> Color.Transparent
                }

                val colorTexto = if (seleccionado) Color.White else Color.Black

                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { vm.seleccionarDia(dia) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorFondo)
                            .padding(8.dp)
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

        // 📌 Fecha seleccionada
        Text("Seleccionado: ${vm.fechaSeleccionada}")

        Spacer(modifier = Modifier.height(8.dp))

        // ✏️ Input
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

        // 📋 Eventos
        Text("Eventos del día", style = MaterialTheme.typography.titleMedium)

        vm.eventos.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = it.titulo,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}