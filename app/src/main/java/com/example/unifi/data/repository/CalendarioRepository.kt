package com.example.unifi.data.repository

import com.example.unifi.data.model.Evento
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CalendarioRepository {

    private val db = FirebaseFirestore.getInstance()

    private val eventosRef = db.collection("eventos")

    // Guardar evento
    suspend fun addEvento(evento: Evento) {

        val docRef = eventosRef.document()

        val nuevoEvento = evento.copy(id = docRef.id)

        docRef.set(nuevoEvento).await()
    }

    // Obtener eventos por fecha
    suspend fun getEventosPorFecha(fecha: String): List<Evento> {

        return eventosRef
            .whereEqualTo("fecha", fecha)
            .get()
            .await()
            .documents
            .mapNotNull { doc ->
                doc.toObject(Evento::class.java)
            }
    }
}