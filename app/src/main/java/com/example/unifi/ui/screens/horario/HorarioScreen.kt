package com.example.unifi.ui.screens.horario

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.viewmodel.HorarioViewModel
import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun HorarioScreen(vm: HorarioViewModel = viewModel()) {

    var materia by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, hour: Int, minute: Int ->
            hora = String.format("%02d:%02d", hour, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )


    var diaSeleccionado by remember { mutableStateOf("Lunes") }

    val colores = listOf(
        Color(0xFFEF9A9A), // rosa suave vivo
        Color(0xFFF48FB1), // rosa más intenso
        Color(0xFFCE93D8), // lila vivo
        Color(0xFFB39DDB), // morado suave
        Color(0xFF9FA8DA), // azul lavanda más marcado
        Color(0xFF90CAF9), // azul cielo vivo
        Color(0xFF80DEEA), // aqua más brillante
        Color(0xFFA5D6A7), // verde fresco
        Color(0xFFFFF176), // amarillo suave vivo
        Color(0xFFFFB74D)  // naranja pastel vivo
    )

    var colorSeleccionado by remember { mutableStateOf(colores[0]) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text("Horario de Clases", style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Selecciona el día:")
        Spacer(modifier = Modifier.height(8.dp))

        // Selector de día
        DropdownMenuDemo(vm.diasSemana, diaSeleccionado) {
            diaSeleccionado = it
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = materia,
            onValueChange = { materia = it },
            label = { Text("Materia") }
        )

        Button(onClick = { timePickerDialog.show() } , modifier = Modifier.fillMaxWidth()) {
            Text(
                if (hora.isEmpty()) "Seleccionar hora"
                else "Hora: $hora"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Selección de color
        Row {
            colores.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(color)
                        .clickable { colorSeleccionado = color }
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (materia.isNotBlank() && hora.isNotBlank()) {
                vm.agregarClase(diaSeleccionado, materia, hora, colorSeleccionado)
                materia = ""
                hora = ""
            }
        }) {
            Text("Agregar Clase")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista por días
        Text("Tu horario", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {

            items(vm.diasSemana.size) { index ->

                val dia = vm.diasSemana[index]
                val clasesDelDia = vm.clases
                    .filter { it.dia == dia }
                    .sortedBy { it.hora }

                Row {

                    // Columna del día
                    Column(
                        modifier = Modifier
                            .width(180.dp)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally // 👈 CENTRADO
                    ) {

                        // Día
                        Text(
                            text = dia,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Clases
                        clasesDelDia.forEach { clase ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(clase.color)
                                        .padding(8.dp)
                                ) {
                                    Text(clase.materia)
                                    Text(clase.hora)

                                    Text(
                                        "Eliminar",
                                        modifier = Modifier.clickable {
                                            vm.eliminarClase(clase)
                                        }
                                    )
                                }
                            }
                        }

                        // 🟡 Sin clases
                        if (clasesDelDia.isEmpty()) {
                            Text(
                                text = "Sin clases",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    // Línea separadora (excepto el último)
                    if (index < vm.diasSemana.size - 1) {
                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuDemo(opciones: List<String>, seleccionado: String, onSelect: (String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(seleccionado)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            opciones.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSelect(it)
                        expanded = false
                    }
                )
            }
        }
    }
}