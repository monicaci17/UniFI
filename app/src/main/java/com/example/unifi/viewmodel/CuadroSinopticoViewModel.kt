package com.example.unifi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.unifi.data.model.CuadroSinoptico
import com.example.unifi.data.model.IdeaSinoptica
import com.example.unifi.data.repository.CuadroSinopticoRepository

class CuadroSinopticoViewModel : ViewModel() {

    private val repository = CuadroSinopticoRepository()

    val cuadros = mutableStateListOf<CuadroSinoptico>()

    init {
        cargarCuadros()
    }

    fun cargarCuadros() {
        repository.obtenerCuadros(
            onResult = { lista ->
                cuadros.clear()
                cuadros.addAll(lista)
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun agregarCuadro(
        titulo: String,
        conceptoGeneral: String,
        ideas: List<IdeaSinoptica>
    ) {
        if (titulo.isNotBlank() && conceptoGeneral.isNotBlank() && ideas.isNotEmpty()) {
            repository.agregarCuadro(
                titulo = titulo,
                conceptoGeneral = conceptoGeneral,
                ideas = ideas,
                onSuccess = {
                    cargarCuadros()
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }

    fun eliminarCuadro(indice: Int) {
        if (indice in cuadros.indices) {
            val cuadroId = cuadros[indice].id

            repository.eliminarCuadro(
                cuadroId = cuadroId,
                onSuccess = {
                    cuadros.removeAt(indice)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }
}