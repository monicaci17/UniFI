package com.example.unifi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unifi.apis.RelajacionApi
import com.example.unifi.data.model.Frases
import kotlinx.coroutines.launch

open class RelajacionViewModel : ViewModel() {

    val frases = mutableStateListOf<Frases>()

    var imagenUrl by mutableStateOf(
        "https://picsum.photos/800/400?random=1"
    )

    init {
        cargarFrases()
    }

    open fun cargarFrases() {

        imagenUrl =
            "https://picsum.photos/800/400?random=${System.currentTimeMillis()}"

        viewModelScope.launch {

            try {

                frases.clear()

                repeat(5) {

                    val respuesta =
                        RelajacionApi.api.getFraseRandom()

                    if (respuesta.isNotEmpty()) {
                        frases.add(respuesta[0])
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}