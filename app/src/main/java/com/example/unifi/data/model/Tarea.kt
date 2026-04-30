package com.example.unifi.data.model

data class Tarea(
    val id: Int,
    val descripcion: String,
    val asignatura: String,
    val estaTerminado: Boolean = false
)