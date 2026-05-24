package com.example.unifi.data.repository

import com.example.unifi.data.model.ForoPost
import com.example.unifi.data.model.ForoRespuesta
import com.google.firebase.firestore.FirebaseFirestore

class ForoRepository {

    private val db = FirebaseFirestore.getInstance()

    fun agregarPost(
        titulo: String,
        contenido: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {

        val post = hashMapOf(
            "titulo" to titulo,
            "contenido" to contenido,
            "creadoEn" to System.currentTimeMillis()
        )

        db.collection("foro_posts")
            .add(post)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it)
            }
    }

    fun obtenerPosts(
        onResult: (List<ForoPost>) -> Unit,
        onError: (Exception) -> Unit
    ) {

        db.collection("foro_posts")
            .orderBy("creadoEn")
            .get()
            .addOnSuccessListener { result ->

                val posts = result.documents.map { document ->

                    ForoPost(
                        id = document.id,
                        titulo = document.getString("titulo") ?: "",
                        contenido = document.getString("contenido") ?: ""
                    )
                }

                onResult(posts)
            }
            .addOnFailureListener {
                onError(it)
            }
    }

    fun agregarRespuesta(
        postId: String,
        texto: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {

        val respuesta = hashMapOf(
            "texto" to texto,
            "creadoEn" to System.currentTimeMillis()
        )

        db.collection("foro_posts")
            .document(postId)
            .collection("respuestas")
            .add(respuesta)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it)
            }
    }

    fun obtenerRespuestas(
        postId: String,
        onResult: (List<ForoRespuesta>) -> Unit,
        onError: (Exception) -> Unit
    ) {

        db.collection("foro_posts")
            .document(postId)
            .collection("respuestas")
            .orderBy("creadoEn")
            .get()
            .addOnSuccessListener { result ->

                val respuestas = result.documents.map { document ->

                    ForoRespuesta(
                        id = document.id,
                        texto = document.getString("texto") ?: ""
                    )
                }

                onResult(respuestas)
            }
            .addOnFailureListener {
                onError(it)
            }
    }

    fun eliminarPost(
        postId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {

        db.collection("foro_posts")
            .document(postId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it)
            }
    }

    fun eliminarRespuesta(
        postId: String,
        respuestaId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {

        db.collection("foro_posts")
            .document(postId)
            .collection("respuestas")
            .document(respuestaId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it)
            }
    }
}