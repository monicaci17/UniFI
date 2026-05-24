package com.example.unifi.data.model

data class ForoRespuesta(
    val id: String = "",
    val texto: String = "",
    val creadoEn: Long = System.currentTimeMillis()
)