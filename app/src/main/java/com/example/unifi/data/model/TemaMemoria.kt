package com.example.unifi.data.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class TemaMemoria(
    val id: String = "",
    val nombre: String = "",
    val tarjetas: SnapshotStateList<TarjetaMemoria> = mutableStateListOf()
)