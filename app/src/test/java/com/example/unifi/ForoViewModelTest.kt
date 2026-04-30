package com.example.unifi

import com.example.unifi.viewmodel.ForoViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ForoViewModelTest {

    private lateinit var viewModel: ForoViewModel

    @Before
    fun setUp() {
        viewModel = ForoViewModel()
    }

    @Test
    fun agregarPost_valido_seAgregaCorrectamente() {
        viewModel.agregarPost("Título", "Contenido")

        assertEquals(1, viewModel.posts.size)
        assertEquals("Título", viewModel.posts[0].titulo)
        assertEquals("Contenido", viewModel.posts[0].contenido)
    }

    @Test
    fun agregarPost_vacio_noSeAgrega() {
        viewModel.agregarPost("", "Contenido")

        assertTrue(viewModel.posts.isEmpty())
    }

    @Test
    fun agregarRespuesta_valida_seAgregaCorrectamente() {
        viewModel.agregarPost("Título", "Contenido")

        viewModel.agregarRespuesta(0, "Respuesta")

        val respuestas = viewModel.posts[0].respuestas
        assertEquals(1, respuestas.size)
        assertEquals("Respuesta", respuestas[0].texto)
    }

    @Test
    fun agregarRespuesta_indiceInvalido_noSeAgrega() {
        viewModel.agregarRespuesta(0, "Respuesta")

        assertTrue(viewModel.posts.isEmpty())
    }

    @Test
    fun agregarRespuesta_textoVacio_noSeAgrega() {
        viewModel.agregarPost("Título", "Contenido")

        viewModel.agregarRespuesta(0, "")

        val respuestas = viewModel.posts[0].respuestas
        assertTrue(respuestas.isEmpty())
    }
}