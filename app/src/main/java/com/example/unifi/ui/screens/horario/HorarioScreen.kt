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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.unifi.data.model.Clase
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
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.secondaryContainer
    )

    var colorSeleccionado by remember { mutableStateOf(colores[0]) }

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
                text = "HORARIO",
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

                Text(
                    "Selecciona el día:",
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                DropdownMenuDemo(vm.diasSemana, diaSeleccionado) {
                    diaSeleccionado = it
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = materia,
                    onValueChange = { materia = it },
                    label = { Text("Materia") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { timePickerDialog.show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (hora.isEmpty()) "Seleccionar hora"
                        else "Hora: $hora"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 🎨 Selección de color
                Row {
                    colores.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(color, shape = MaterialTheme.shapes.small)
                                .clickable { colorSeleccionado = color }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (materia.isNotBlank() && hora.isNotBlank()) {
                            vm.agregarClase(diaSeleccionado, materia, hora, colorSeleccionado)
                            materia = ""
                            hora = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar Clase")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Tu horario",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow {

                    items(vm.diasSemana.size) { index ->

                        val dia = vm.diasSemana[index]
                        val clasesDelDia = vm.clases
                            .filter { it.dia == dia }
                            .sortedBy { it.hora }

                        Row {

                            Column(
                                modifier = Modifier
                                    .width(180.dp)
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = dia,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                clasesDelDia.forEach { clase ->

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(
                                                android.graphics.Color.parseColor(clase.colorHex)
                                            )
                                        )
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                        ) {
                                            Text(
                                                clase.materia,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                            Text(
                                                clase.hora,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )

                                            Text(
                                                "Eliminar",
                                                color = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.clickable {
                                                    vm.eliminarClase(clase)
                                                }
                                            )
                                        }
                                    }
                                }

                                if (clasesDelDia.isEmpty()) {
                                    Text(
                                        text = "Sin clases",

                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }

                            if (index < vm.diasSemana.size - 1) {
                                VerticalDivider(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(2.dp),
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
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