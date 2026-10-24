package com.example.desarrollo_mobile.model

enum class EstadoCandidatura(val etiqueta: String, val estaResuelta: Boolean) {
    POSTULADA("Postulada", false),
    EN_EVALUACION("En evaluación", false),
    EVALUADA("Evaluada", false),
    SELECCIONADA("Seleccionada", true),
    NO_SELECCIONADA("No seleccionada", true)
}