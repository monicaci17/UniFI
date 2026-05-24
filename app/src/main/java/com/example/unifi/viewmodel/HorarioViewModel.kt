package com.example.unifi.viewmodel

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unifi.data.model.Clase
import com.example.unifi.data.repository.HorarioRepository
import kotlinx.coroutines.launch

class HorarioViewModel : ViewModel() {

    private val repository = HorarioRepository()

    val diasSemana = listOf(
        "Lunes", "Martes", "Miércoles",
        "Jueves", "Viernes", "Sábado", "Domingo"
    )

    var clases = mutableStateListOf<Clase>()
        private set

    init {
        cargarClases()
    }

    private fun cargarClases() {

        viewModelScope.launch {

            val lista = repository.getClases()

            clases.clear()
            clases.addAll(lista)
        }
    }

    fun agregarClase(
        dia: String,
        materia: String,
        hora: String,
        color: Color
    ) {

        viewModelScope.launch {

            val hex = String.format(
                "#%06X",
                0xFFFFFF and color.toArgb()
            )

            val nueva = Clase(
                dia = dia,
                materia = materia,
                hora = hora,
                colorHex = hex
            )

            repository.agregarClase(nueva)

            cargarClases()
        }
    }

    fun eliminarClase(clase: Clase) {

        viewModelScope.launch {

            repository.eliminarClase(clase)

            cargarClases()
        }
    }
}