package com.example.unifi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unifi.apis.RelajacionApi
import com.example.unifi.data.model.Frases
import kotlinx.coroutines.launch

open class RelajacionViewModel : ViewModel() {

    val frases = mutableStateListOf<Frases>()

    init {
        cargarFrases()
    }

    open fun cargarFrases() {
        viewModelScope.launch {
            try {
                frases.clear()

                repeat(5) {
                    val respuesta = RelajacionApi.api.getFraseRandom()

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