package com.example.unifi.data.model

data class TemaMemoria(
    val nombre: String,
    val tarjetas: MutableList<TarjetaMemoria> = mutableListOf()
)