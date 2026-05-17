package com.example.unifi.viewmodel

import com.example.unifi.data.model.Nota
import com.example.unifi.data.model.Tarea
import com.example.unifi.data.model.Meta
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class PendienteViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val notasRef = db.collection("notas")
    private val tareasRef = db.collection("tareas")
    private val metasRef = db.collection("metas")

    var listaNotas = mutableStateListOf<Nota>()
        private set

    var listaTareas = mutableStateListOf<Tarea>()
        private set

    var listaMetas = mutableStateListOf<Meta>()
        private set

    init {
        fetchNotas()
        fetchTareas()
        fetchMetas()
    }

    private fun fetchNotas() {
        notasRef.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) return@addSnapshotListener
            listaNotas.clear()
            listaNotas.addAll(snapshot.toObjects(Nota::class.java))
        }
    }

    fun agregarNota(titulo: String, contenido: String) {
        val documentId = notasRef.document().id // Genera ID único automático
        val nuevaNota = Nota(id = documentId, titulo = titulo, contenido = contenido)
        notasRef.document(documentId).set(nuevaNota)
    }

    fun eliminarNota(nota: Nota) {
        notasRef.document(nota.id).delete()
    }

    private fun fetchTareas() {
        tareasRef.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) return@addSnapshotListener
            listaTareas.clear()
            listaTareas.addAll(snapshot.toObjects(Tarea::class.java))
        }
    }

    fun agregarTarea(descripcion: String, asignatura: String) {
        val documentId = tareasRef.document().id
        val nuevaTarea = Tarea(
            id = documentId,
            asignatura = asignatura,
            descripcion = descripcion,
            estaTerminado = false
        )
        tareasRef.document(documentId).set(nuevaTarea)
    }

    fun cambiarEstadoTarea(tarea: Tarea) {
        val tareaActualizada = tarea.copy(estaTerminado = !tarea.estaTerminado)
        tareasRef.document(tarea.id).set(tareaActualizada)
    }

    fun eliminarTarea(tarea: Tarea) {
        tareasRef.document(tarea.id).delete()
    }

    private fun fetchMetas() {
        metasRef.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) return@addSnapshotListener
            listaMetas.clear()
            listaMetas.addAll(snapshot.toObjects(Meta::class.java))
        }
    }

    fun agregarMeta(descripcion: String) {
        val documentId = metasRef.document().id
        val nuevaMeta = Meta(id = documentId, descripcion = descripcion, estaTerminado = false)
        metasRef.document(documentId).set(nuevaMeta)
    }

    fun cambiarEstadoMeta(meta: Meta) {
        val metaActualizada = meta.copy(estaTerminado = !meta.estaTerminado)
        metasRef.document(meta.id).set(metaActualizada)
    }

    fun eliminarMeta(meta: Meta) {
        metasRef.document(meta.id).delete()
    }
}