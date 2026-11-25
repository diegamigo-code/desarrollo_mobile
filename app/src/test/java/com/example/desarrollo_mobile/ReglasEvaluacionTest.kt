package com.example.desarrollo_mobile

import com.example.desarrollo_mobile.model.CalculadoraPuntaje
import com.example.desarrollo_mobile.validation.EvaluacionValidator
import org.junit.Assert.*
import org.junit.Test

class ReglasEvaluacionTest {
    @Test fun ponderacionConservaLaEscalaSobreCien() {
        val notas = listOf(5 to 25, 4 to 20, 3 to 20, 2 to 20, 1 to 15)
        assertEquals(64.0, CalculadoraPuntaje.calcularPuntajePonderado(notas), 0.001)
    }

    @Test fun notaBajaRequiereJustificacion() {
        assertFalse(EvaluacionValidator.validarObservacionCriterio(2, "").esValido)
        assertTrue(EvaluacionValidator.validarObservacionCriterio(2, "Justificación suficiente").esValido)
    }
}
