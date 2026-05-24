package com.example.unifi.data.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class ForoPost(
    val id: String = "",
    val titulo: String = "",
    val contenido: String = "",
    val respuestas: SnapshotStateList<ForoRespuesta> = mutableStateListOf(),
    val creadoEn: Long = System.currentTimeMillis()
)