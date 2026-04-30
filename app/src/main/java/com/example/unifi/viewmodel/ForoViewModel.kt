package com.example.unifi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.unifi.data.model.ForoPost
import com.example.unifi.data.model.ForoRespuesta

class ForoViewModel : ViewModel() {

    val posts = mutableStateListOf<ForoPost>()

    fun agregarPost(titulo: String, contenido: String) {
        if (titulo.isNotBlank() && contenido.isNotBlank()) {
            posts.add(
                0,
                ForoPost(
                    titulo = titulo,
                    contenido = contenido
                )
            )
        }
    }

    fun agregarRespuesta(indicePost: Int, texto: String) {
        if (indicePost in posts.indices && texto.isNotBlank()) {
            posts[indicePost].respuestas.add(
                ForoRespuesta(texto = texto)
            )
        }
    }
}