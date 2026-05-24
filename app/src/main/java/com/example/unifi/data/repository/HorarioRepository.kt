package com.example.unifi.data.repository

import com.example.unifi.data.model.Clase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HorarioRepository {

    private val db = FirebaseFirestore.getInstance()

    private val clasesRef = db.collection("horario")

    // Obtener clases
    suspend fun getClases(): List<Clase> {

        return clasesRef
            .get()
            .await()
            .documents
            .mapNotNull { doc ->
                doc.toObject(Clase::class.java)
            }
    }

    // Agregar clase
    suspend fun agregarClase(clase: Clase) {

        val docRef = clasesRef.document()

        val nuevaClase = clase.copy(id = docRef.id)

        docRef.set(nuevaClase).await()
    }

    // Eliminar clase
    suspend fun eliminarClase(clase: Clase) {

        clasesRef
            .document(clase.id)
            .delete()
            .await()
    }
}