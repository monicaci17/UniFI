package com.example.unifi.data.model

data class CuadroSinoptico(
    val id: String = "",
    val titulo: String = "",
    val conceptoGeneral: String = "",
    val ideas: List<IdeaSinoptica> = emptyList(),
    val creadoEn: Long = System.currentTimeMillis()
)