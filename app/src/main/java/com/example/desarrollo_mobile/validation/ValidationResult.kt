package com.example.desarrollo_mobile.validation

sealed class ValidationResult {
    object Valido : ValidationResult()
    data class Error(val mensaje: String) : ValidationResult()

    val esValido: Boolean get() = this is Valido
    val errorOrNull: String? get() = (this as? Error)?.mensaje
}