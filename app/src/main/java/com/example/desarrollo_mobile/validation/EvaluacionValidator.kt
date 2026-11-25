package com.example.desarrollo_mobile.validation

object EvaluacionValidator {

    fun validarPuntaje(puntaje: Int): ValidationResult {
        if (puntaje !in 1..5) return ValidationResult.Error("El puntaje debe estar entre 1 y 5")
        return ValidationResult.Valido
    }

    fun validarObservacionCriterio(puntaje: Int, observacion: String): ValidationResult {
        val limpia = observacion.trim()
        if (puntaje <= 2 && limpia.length < 10) {
            return ValidationResult.Error("Para puntajes de 1 o 2 es obligatoria una justificación (mínimo 10 caracteres)")
        }
        return ValidationResult.Valido
    }

    fun validarObservacionGeneral(observacion: String): ValidationResult {
        val limpia = observacion.trim()
        if (limpia.length < 20 || limpia.length > 400) {
            return ValidationResult.Error("La observación general debe tener entre 20 y 400 caracteres")
        }
        return ValidationResult.Valido
    }
}