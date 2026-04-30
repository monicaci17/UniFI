package com.example.unifi.data.repository

import com.example.unifi.data.model.Clase

class HorarioRepository {

    private val clases = mutableListOf<Clase>()

    fun getClases(): List<Clase> = clases

    fun agregarClase(clase: Clase) {
        clases.add(clase)
    }

    fun eliminarClase(clase: Clase) {
        clases.remove(clase)
    }
}