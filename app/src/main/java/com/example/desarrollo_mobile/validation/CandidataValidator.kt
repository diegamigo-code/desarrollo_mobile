package com.example.desarrollo_mobile.validation

object CandidataValidator {

    fun validarNombre(nombre: String): ValidationResult {
        val limpio = nombre.trim()
        if (limpio.isBlank()) return ValidationResult.Error(mensaje = "El nombre es obligatorio")
        if (limpio.length < 3) return ValidationResult.Error(mensaje = "El nombre debe tener al menos 3 caracteres")
        return ValidationResult.Valido
    }

    fun validarCorreo(correo: String): ValidationResult {
        val limpio = correo.trim().lowercase()
        if (limpio.isBlank()) return ValidationResult.Error(mensaje = "El correo es obligatorio")
        val dominiosPermitidos = listOf("ejemplo.cl", "example.com", "test.cl")
        val patronEmail = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        if (!limpio.matches(Regex(pattern = patronEmail))) return ValidationResult.Error(mensaje = "Formato de correo inválido")
        val dominio = limpio.substringAfter(delimiter = "@")
        if (dominio !in dominiosPermitidos) return ValidationResult.Error(mensaje = "Dominio no permitido (use ejemplo.cl, example.com o test.cl)")
        return ValidationResult.Valido
    }

    fun validarTelefono(telefono: String): ValidationResult {
        val limpio = telefono.trim()
        if (limpio.isBlank()) return ValidationResult.Error(mensaje = "El teléfono es obligatorio")
        val patronTelefono = "^(\\+?56)?\\s?9\\s?\\d{4}\\s?\\d{4}$"
        if (!limpio.matches(Regex(pattern = patronTelefono))) return ValidationResult.Error(mensaje = "Formato chileno inválido (+56 9 XXXX XXXX)")
        return ValidationResult.Valido
    }

    fun validarAnosExperiencia(anosText: String): ValidationResult {
        val anos = anosText.toIntOrNull() ?: return ValidationResult.Error(mensaje = "Debe ingresar un número entero")
        if (anos < 0 || anos > 50) return ValidationResult.Error(mensaje = "Los años deben estar entre 0 y 50")
        return ValidationResult.Valido
    }

    fun validarResumen(resumen: String): ValidationResult {
        val limpio = resumen.trim()
        if (limpio.isBlank()) return ValidationResult.Error(mensaje = "El resumen es obligatorio")
        if (limpio.length < 30 || limpio.length > 500) return ValidationResult.Error(mensaje = "El resumen debe tener entre 30 y 500 caracteres")
        return ValidationResult.Valido
    }

    fun validarEnlacePerfil(enlace: String): ValidationResult {
        val limpio = enlace.trim()
        if (limpio.isBlank()) return ValidationResult.Error(mensaje = "El perfil de LinkedIn es obligatorio")
        if (!limpio.startsWith("http://") && !limpio.startsWith("https://")) {
            return ValidationResult.Error(mensaje = "El enlace debe comenzar con http:// o https://")
        }
        return ValidationResult.Valido
    }

    fun validarCvUri(uri: String): ValidationResult {
        val limpio = uri.trim()
        if (limpio.isBlank()) return ValidationResult.Error(mensaje = "Debe adjuntar un archivo CV")
        return ValidationResult.Valido
    }
}
