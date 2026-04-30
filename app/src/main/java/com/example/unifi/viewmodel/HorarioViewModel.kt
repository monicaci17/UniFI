package com.example.unifi.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.unifi.data.model.Clase
import com.example.unifi.data.repository.HorarioRepository
import androidx.compose.ui.graphics.Color

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
        clases.clear()
        clases.addAll(repository.getClases())
    }

    fun agregarClase(dia: String, materia: String, hora: String, color: Color) {
        val nueva = Clase(
            id = (0..100000).random(),
            dia = dia,
            materia = materia,
            hora = hora,
            color = color
        )

        repository.agregarClase(nueva)
        cargarClases()
    }

    fun eliminarClase(clase: Clase) {
        repository.eliminarClase(clase)
        cargarClases()
    }
}