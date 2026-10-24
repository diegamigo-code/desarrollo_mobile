package com.example.desarrollo_mobile.model

data class ResultadoConsolidado(
    val candidataId: Long,
    val candidataNombre: String,
    val area: String,
    val estado: EstadoCandidatura,
    val cantidadEvaluaciones: Int,
    val promedioPuntaje: Double
)