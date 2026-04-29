package com.example.unifi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.unifi.data.repository.NotaRepository
import com.example.unifi.data.model.Nota

class NotaViewModel : ViewModel() {

    private val repository = NotaRepository()

    var notas = mutableStateListOf<Nota>()
        private set

    init {
        loadNotas()
    }

    fun loadNotas() {
        notas.clear()
        notas.addAll(repository.getNotas())
    }

    fun agregarNota(titulo: String, contenido: String) {
        val nota = Nota(notas.size + 1, titulo, contenido)
        repository.addNota(nota)
        loadNotas()
    }

    fun eliminarNota(nota: Nota) {
        repository.deleteNota(nota)
        loadNotas()
    }
}