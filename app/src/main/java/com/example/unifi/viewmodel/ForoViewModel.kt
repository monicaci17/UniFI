package com.example.unifi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.unifi.data.model.ForoPost
import com.example.unifi.data.model.ForoRespuesta
import com.example.unifi.data.repository.ForoRepository

class ForoViewModel : ViewModel() {

    private val repository = ForoRepository()

    val posts = mutableStateListOf<ForoPost>()

    init {
        cargarPosts()
    }

    fun cargarPosts() {

        repository.obtenerPosts(
            onResult = { listaPosts ->

                posts.clear()
                posts.addAll(listaPosts)
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun agregarPost(
        titulo: String,
        contenido: String
    ) {

        if (titulo.isNotBlank() && contenido.isNotBlank()) {

            repository.agregarPost(
                titulo = titulo,
                contenido = contenido,
                onSuccess = {
                    cargarPosts()
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }

    fun cargarRespuestas(indicePost: Int) {

        if (indicePost in posts.indices) {

            val postId = posts[indicePost].id

            repository.obtenerRespuestas(
                postId = postId,
                onResult = { listaRespuestas ->

                    posts[indicePost].respuestas.clear()
                    posts[indicePost].respuestas.addAll(listaRespuestas)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }

    fun agregarRespuesta(
        indicePost: Int,
        texto: String
    ) {

        if (
            indicePost in posts.indices &&
            texto.isNotBlank()
        ) {

            val postId = posts[indicePost].id

            repository.agregarRespuesta(
                postId = postId,
                texto = texto,
                onSuccess = {
                    cargarRespuestas(indicePost)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }

    fun eliminarPost(indicePost: Int) {

        if (indicePost in posts.indices) {

            val postId = posts[indicePost].id

            repository.eliminarPost(
                postId = postId,
                onSuccess = {
                    posts.removeAt(indicePost)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }

    fun eliminarRespuesta(
        indicePost: Int,
        indiceRespuesta: Int
    ) {

        if (
            indicePost in posts.indices &&
            indiceRespuesta in posts[indicePost].respuestas.indices
        ) {

            val postId = posts[indicePost].id

            val respuestaId =
                posts[indicePost]
                    .respuestas[indiceRespuesta]
                    .id

            repository.eliminarRespuesta(
                postId = postId,
                respuestaId = respuestaId,
                onSuccess = {

                    posts[indicePost]
                        .respuestas
                        .removeAt(indiceRespuesta)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }
}