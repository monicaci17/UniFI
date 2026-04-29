package com.example.unifi.data.repository

import com.example.unifi.data.model.Nota

class NotaRepository {

    private val notas = mutableListOf<Nota>()

    fun getNotas(): List<Nota> = notas

    fun addNota(nota: Nota) {
        notas.add(nota)
    }

    fun deleteNota(nota: Nota) {
        notas.remove(nota)
    }
}