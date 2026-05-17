package com.example.unifi.data.model

data class Tarea(
    val id: String = "",
    val asignatura: String = "",
    val descripcion: String = "",
    val estaTerminado: Boolean = false
)