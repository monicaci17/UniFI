package com.example.unifi.data.model

data class User(
    val nombre: String,
    val correo: String,
    val usuario: String,
    val temaOscuro: Boolean = false
)
