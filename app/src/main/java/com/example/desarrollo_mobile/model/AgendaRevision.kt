package com.example.desarrollo_mobile.model

data class AgendaRevision(
    val total: Int,
    val conEvaluacion: Int,
    val pendientes: Int,
    val seleccionadas: Int,
    val siguiente: ResultadoConsolidado?
) {
    companion object {
        fun desde(filas: List<ResultadoConsolidado>): AgendaRevision {
            val pendientes = filas.filter {
                it.estado == EstadoCandidatura.POSTULADA && it.cantidadEvaluaciones == 0
            }
            return AgendaRevision(filas.size, filas.count { it.cantidadEvaluaciones > 0 },
                pendientes.size, filas.count { it.estado == EstadoCandidatura.SELECCIONADA },
                pendientes.minByOrNull { it.candidataId })
        }
    }
}
