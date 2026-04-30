package com.example.unifi.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.unifi.data.model.Evento
import com.example.unifi.data.repository.CalendarioRepository
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class CalendarioViewModel : ViewModel() {

    private val repository = CalendarioRepository()

    // 📅 Fecha actual real
    @RequiresApi(Build.VERSION_CODES.O)
    val fechaHoy: LocalDate = LocalDate.now()

    // 📆 Estado actual del calendario
    var mesActual by mutableStateOf(fechaHoy.monthValue)
    var añoActual by mutableStateOf(fechaHoy.year)

    // 📌 Día seleccionado
    var fechaSeleccionada by mutableStateOf("")

    // 📌 Eventos del día seleccionado
    var eventos = mutableStateListOf<Evento>()
        private set

    // 🔢 Obtener número de días del mes
    fun obtenerDiasDelMes(): Int {
        val yearMonth = YearMonth.of(añoActual, mesActual)
        return yearMonth.lengthOfMonth()
    }

    // 🏷️ Nombre del mes (en español)
    fun nombreMes(): String {
        return LocalDate.of(añoActual, mesActual, 1)
            .month
            .getDisplayName(TextStyle.FULL, Locale("es"))
            .replaceFirstChar { it.uppercase() }
    }

    // ➡️ Siguiente mes (limitado a 2026)
    fun siguienteMes() {
        if (añoActual == 2026 && mesActual < 12) {
            mesActual++
        }
    }

    // ⬅️ Mes anterior (limitado a 2026)
    fun mesAnterior() {
        if (añoActual == 2026 && mesActual > 1) {
            mesActual--
        }
    }

    // 📅 Seleccionar día
    fun seleccionarDia(dia: Int) {
        fechaSeleccionada = "$añoActual-${mesActual.toString().padStart(2, '0')}-${dia.toString().padStart(2, '0')}"
        cargarEventos()
    }

    // ➕ Agregar evento
    fun agregarEvento(titulo: String) {
        if (fechaSeleccionada.isBlank()) return

        val evento = Evento(
            id = eventos.size + 1,
            fecha = fechaSeleccionada,
            titulo = titulo
        )

        repository.addEvento(evento)
        cargarEventos()
    }

    // 🔄 Cargar eventos del día seleccionado
    private fun cargarEventos() {
        eventos.clear()
        eventos.addAll(repository.getEventosPorFecha(fechaSeleccionada))
    }
}