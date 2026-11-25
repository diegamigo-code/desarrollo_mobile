package com.example.desarrollo_mobile

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class NavegacionTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    @Test fun accesoPerfilYCierreDeSesion() {
        compose.onNodeWithText("Evaluadora").performScrollTo().performClick()
        compose.onNodeWithText("Continuar").performScrollTo().performClick()
        compose.waitUntil(timeoutMillis = 20_000) {
            compose.onAllNodesWithText("Perfil").fetchSemanticsNodes().isNotEmpty()
        }
        compose.onNodeWithText("Perfil").performClick()
        compose.onNodeWithText("evaluadora", ignoreCase = false).assertExists()
        compose.onNodeWithText("Cerrar sesión").performClick()
        compose.waitUntil(timeoutMillis = 20_000) {
            compose.onAllNodesWithText("Continuar").fetchSemanticsNodes().isNotEmpty()
        }
        compose.onNodeWithText("Continuar").assertExists()
    }
}
