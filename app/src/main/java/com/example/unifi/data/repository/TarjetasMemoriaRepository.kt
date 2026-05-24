package com.example.unifi.data.repository

import com.example.unifi.data.model.TarjetaMemoria
import com.example.unifi.data.model.TemaMemoria
import com.google.firebase.firestore.FirebaseFirestore

class TarjetasMemoriaRepository {

    private val db = FirebaseFirestore.getInstance()

    fun agregarTema(
        nombre: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val tema = hashMapOf(
            "nombre" to nombre,
            "creadoEn" to System.currentTimeMillis()
        )

        db.collection("temas_memoria")
            .add(tema)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }

    fun obtenerTemas(
        onResult: (List<TemaMemoria>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("temas_memoria")
            .get()
            .addOnSuccessListener { result ->
                val temas = result.documents.map { document ->
                    TemaMemoria(
                        id = document.id,
                        nombre = document.getString("nombre") ?: ""
                    )
                }

                onResult(temas)
            }
            .addOnFailureListener { onError(it) }
    }

    fun agregarTarjeta(
        temaId: String,
        pregunta: String,
        respuesta: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val tarjeta = hashMapOf(
            "pregunta" to pregunta,
            "respuesta" to respuesta,
            "creadoEn" to System.currentTimeMillis()
        )

        db.collection("temas_memoria")
            .document(temaId)
            .collection("tarjetas")
            .add(tarjeta)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }

    fun obtenerTarjetas(
        temaId: String,
        onResult: (List<TarjetaMemoria>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("temas_memoria")
            .document(temaId)
            .collection("tarjetas")
            .get()
            .addOnSuccessListener { result ->
                val tarjetas = result.documents.map { document ->
                    TarjetaMemoria(
                        id = document.id,
                        pregunta = document.getString("pregunta") ?: "",
                        respuesta = document.getString("respuesta") ?: ""
                    )
                }

                onResult(tarjetas)
            }
            .addOnFailureListener { onError(it) }
    }

    fun eliminarTema(
        temaId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("temas_memoria")
            .document(temaId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }

    fun eliminarTarjeta(
        temaId: String,
        tarjetaId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("temas_memoria")
            .document(temaId)
            .collection("tarjetas")
            .document(tarjetaId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }
}

