package com.example.unifi

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.unifi.data.model.Frases
import com.example.unifi.ui.screens.relajacion.RelajacionScreen
import com.example.unifi.viewmodel.RelajacionViewModel
import org.junit.Rule
import org.junit.Test

class RelajacionScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun elementos_basicos_se_muestran() {

        val vm = RelajacionViewModel()

        composeTestRule.setContent {
            RelajacionScreen(relajacionViewModel = vm)
        }

        composeTestRule.onNodeWithText("Relajación y Motivación").assertExists()
        composeTestRule.onNodeWithText("Cargar nuevas frases").assertExists()
    }

    @Test
    fun mostrar_frases_en_pantalla() {

        val vm = RelajacionViewModel()

        vm.frases.clear()
        vm.frases.add(Frases("Frase test", "Autor test"))

        composeTestRule.setContent {
            RelajacionScreen(relajacionViewModel = vm)
        }

        composeTestRule.onNodeWithText("\"Frase test\"").assertExists()
        composeTestRule.onNodeWithText("- Autor test").assertExists()
    }
}