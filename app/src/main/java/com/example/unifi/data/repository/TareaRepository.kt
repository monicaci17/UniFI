package com.example.unifi.data.repository

import com.example.unifi.data.model.Tarea

class TareaRepository {

    private val tareas = mutableListOf<Tarea>()

    fun getTareas(): List<Tarea> = tareas

    fun addTarea(tarea: Tarea) {
        tareas.add(tarea)
    }

    fun deleteTarea(tarea: Tarea) {
        tareas.remove(tarea)
    }

    fun updateTarea(tareaModificada: Tarea) {
        val index = tareas.indexOfFirst { it.id == tareaModificada.id }
        if (index != -1) {
            tareas[index] = tareaModificada
        }
    }
}