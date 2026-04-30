package com.example.unifi

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.unifi.ui.screens.horario.HorarioScreen
import com.example.unifi.viewmodel.HorarioViewModel
import org.junit.Rule
import org.junit.Test

class HorarioScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun elementos_basicos_se_muestran() {

        val vm = HorarioViewModel()

        composeTestRule.setContent {
            HorarioScreen(vm = vm)
        }

        composeTestRule.onNodeWithText("Horario de Clases").assertExists()
        composeTestRule.onNodeWithText("Materia").assertExists()
        composeTestRule.onNodeWithText("Seleccionar hora").assertExists()
        composeTestRule.onNodeWithText("Agregar Clase").assertExists()
    }

    @Test
    fun escribir_materia_funciona() {

        val vm = HorarioViewModel()

        composeTestRule.setContent {
            HorarioScreen(vm = vm)
        }

        composeTestRule
            .onNodeWithText("Materia")
            .performTextInput("Matemáticas")

        composeTestRule
            .onNodeWithText("Matemáticas")
            .assertExists()
    }
}