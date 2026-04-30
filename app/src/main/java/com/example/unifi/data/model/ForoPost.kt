package com.example.unifi.data.model

data class ForoPost(
    val titulo: String,
    val contenido: String,
    val respuestas: MutableList<ForoRespuesta> = mutableListOf()
)