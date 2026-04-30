package com.example.unifi.data.repository

import com.example.unifi.data.model.Evento

class CalendarioRepository {

    private val eventos = mutableListOf<Evento>()

    fun getEventos(): List<Evento> = eventos

    fun addEvento(evento: Evento) {
        eventos.add(evento)
    }

    fun getEventosPorFecha(fecha: String): List<Evento> {
        return eventos.filter { it.fecha == fecha }
    }
}