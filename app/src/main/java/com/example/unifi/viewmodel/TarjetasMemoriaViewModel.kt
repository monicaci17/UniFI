package com.example.unifi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.unifi.data.model.TarjetaMemoria
import com.example.unifi.data.model.TemaMemoria

class TarjetasMemoriaViewModel : ViewModel() {

    val temas = mutableStateListOf<TemaMemoria>()

    fun agregarTema(nombre: String) {
        if (nombre.isNotBlank()) {
            temas.add(
                TemaMemoria(
                    nombre = nombre
                )
            )
        }
    }

    fun agregarTarjeta(
        indiceTema: Int,
        pregunta: String,
        respuesta: String
    ) {
        if (
            indiceTema in temas.indices &&
            pregunta.isNotBlank() &&
            respuesta.isNotBlank()
        ) {
            temas[indiceTema].tarjetas.add(
                TarjetaMemoria(
                    pregunta = pregunta,
                    respuesta = respuesta
                )
            )
        }
    }
}