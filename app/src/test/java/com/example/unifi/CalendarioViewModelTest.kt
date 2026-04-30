package com.example.unifi

import com.example.unifi.viewmodel.CalendarioViewModel
import org.junit.Assert.*
import org.junit.Test

class CalendarioViewModelTest {

    @Test
    fun seleccionarDia_actualizaFecha() {
        val vm = CalendarioViewModel()

        vm.mesActual = 5
        vm.añoActual = 2026

        vm.seleccionarDia(10)

        assertEquals("2026-05-10", vm.fechaSeleccionada)
    }

    @Test
    fun agregarEvento_funciona() {
        val vm = CalendarioViewModel()

        vm.mesActual = 5
        vm.añoActual = 2026
        vm.seleccionarDia(10)

        vm.agregarEvento("Examen")

        assertEquals(1, vm.eventos.size)
        assertEquals("Examen", vm.eventos[0].titulo)
    }
}