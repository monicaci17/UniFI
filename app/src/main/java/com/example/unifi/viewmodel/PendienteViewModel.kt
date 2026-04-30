package com.example.unifi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.unifi.data.model.Meta
import com.example.unifi.data.model.Tarea
import com.example.unifi.data.repository.MetaRepository
import com.example.unifi.data.repository.TareaRepository

class PendienteViewModel : ViewModel() {

    private val tareaRepository = TareaRepository()
    private val metaRepository = MetaRepository()

    // Listas observables para la UI
    var listaTareas = mutableStateListOf<Tarea>()
        private set

    var listaMetas = mutableStateListOf<Meta>()
        private set

    init {
        loadTareas()
        loadMetas()
    }

    // --- LÓGICA DE TAREAS ---
    fun loadTareas() {
        listaTareas.clear()
        listaTareas.addAll(tareaRepository.getTareas())
    }

    fun agregarTarea(descripcion: String, asignatura: String) {
        val nuevaTarea = Tarea(
            id = listaTareas.size + 1,
            descripcion = descripcion,
            asignatura = asignatura,
            estaTerminado = false
        )
        tareaRepository.addTarea(nuevaTarea)
        loadTareas()
    }

    fun eliminarTarea(tarea: Tarea) {
        tareaRepository.deleteTarea(tarea)
        loadTareas()
    }

    fun cambiarEstadoTarea(tarea: Tarea) {
        val tareaActualizada = tarea.copy(estaTerminado = !tarea.estaTerminado)
        tareaRepository.updateTarea(tareaActualizada)
        loadTareas()
    }

    // --- LÓGICA DE METAS ---
    fun loadMetas() {
        listaMetas.clear()
        listaMetas.addAll(metaRepository.getMetas())
    }

    fun agregarMeta(descripcion: String) {
        val nuevaMeta = Meta(
            id = listaMetas.size + 1,
            descripcion = descripcion,
            estaTerminado = false
        )
        metaRepository.addMeta(nuevaMeta)
        loadMetas()
    }

    fun eliminarMeta(meta: Meta) {
        metaRepository.deleteMeta(meta)
        loadMetas()
    }

    fun cambiarEstadoMeta(meta: Meta) {
        val metaActualizada = meta.copy(estaTerminado = !meta.estaTerminado)
        metaRepository.updateMeta(metaActualizada)
        loadMetas()
    }
}