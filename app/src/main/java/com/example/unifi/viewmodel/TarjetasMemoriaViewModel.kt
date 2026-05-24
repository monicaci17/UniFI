package com.example.unifi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.unifi.data.model.TarjetaMemoria
import com.example.unifi.data.model.TemaMemoria
import com.example.unifi.data.repository.TarjetasMemoriaRepository

class TarjetasMemoriaViewModel : ViewModel() {

    private val repository = TarjetasMemoriaRepository()

    val temas = mutableStateListOf<TemaMemoria>()

    init {
        cargarTemas()
    }

    fun cargarTemas() {
        repository.obtenerTemas(
            onResult = { listaTemas ->
                temas.clear()
                temas.addAll(listaTemas)
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun agregarTema(nombre: String) {
        if (nombre.isNotBlank()) {
            repository.agregarTema(
                nombre = nombre,
                onSuccess = {
                    cargarTemas()
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }

    fun cargarTarjetas(indiceTema: Int) {
        if (indiceTema in temas.indices) {
            val temaId = temas[indiceTema].id

            repository.obtenerTarjetas(
                temaId = temaId,
                onResult = { listaTarjetas ->
                    temas[indiceTema].tarjetas.clear()
                    temas[indiceTema].tarjetas.addAll(listaTarjetas)
                },
                onError = {
                    it.printStackTrace()
                }
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
            val temaId = temas[indiceTema].id

            repository.agregarTarjeta(
                temaId = temaId,
                pregunta = pregunta,
                respuesta = respuesta,
                onSuccess = {
                    cargarTarjetas(indiceTema)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }
    fun eliminarTema(indiceTema: Int) {
        if (indiceTema in temas.indices) {
            val temaId = temas[indiceTema].id

            repository.eliminarTema(
                temaId = temaId,
                onSuccess = {
                    temas.removeAt(indiceTema)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }

    fun eliminarTarjeta(
        indiceTema: Int,
        indiceTarjeta: Int
    ) {
        if (
            indiceTema in temas.indices &&
            indiceTarjeta in temas[indiceTema].tarjetas.indices
        ) {
            val temaId = temas[indiceTema].id
            val tarjetaId = temas[indiceTema].tarjetas[indiceTarjeta].id

            repository.eliminarTarjeta(
                temaId = temaId,
                tarjetaId = tarjetaId,
                onSuccess = {
                    temas[indiceTema].tarjetas.removeAt(indiceTarjeta)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }
}