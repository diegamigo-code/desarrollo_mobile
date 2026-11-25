package com.example.desarrollo_mobile

import com.example.desarrollo_mobile.model.*
import org.junit.Assert.*
import org.junit.Test

class AgendaRevisionTest {
    private fun fila(id: Long, estado: EstadoCandidatura, notas: Int) =
        ResultadoConsolidado(id, "Candidata", "TI", estado, notas, 0.0)

    @Test fun priorizaPorOrdenDeRegistroEntrePostuladasSinNotas() {
        val a = AgendaRevision.desde(listOf(fila(6, EstadoCandidatura.POSTULADA, 0),
            fila(1, EstadoCandidatura.SELECCIONADA, 2), fila(4, EstadoCandidatura.POSTULADA, 0),
            fila(2, EstadoCandidatura.EN_EVALUACION, 1)))
        assertEquals(4L, a.siguiente?.candidataId)
        assertEquals(2, a.conEvaluacion)
        assertEquals(2, a.pendientes)
        assertEquals(1, a.seleccionadas)
    }

    @Test fun sinPostulacionesNoSugiereCandidata() {
        assertNull(AgendaRevision.desde(emptyList()).siguiente)
        assertNull(AgendaRevision.desde(listOf(fila(1, EstadoCandidatura.POSTULADA, 1))).siguiente)
    }
}
