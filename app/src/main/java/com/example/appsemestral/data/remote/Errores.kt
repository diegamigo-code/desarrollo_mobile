package com.example.appsemestral.data.remote

import retrofit2.HttpException
import java.io.IOException

fun Throwable.mensajeAmigable(): String = when (this) {
    is HttpException -> when (code()) {
        400 -> "Datos inválidos: revisa el formulario"
        401 -> if (esLogin()) "Usuario o contraseña incorrectos"
               else "Tu sesión expiró. Vuelve a ingresar"
        403 -> "Tu perfil no tiene permisos para esta acción"
        404 -> "El registro ya no existe en el servidor"
        409 -> "Ya existe una candidatura con este correo"
        else -> "Error del servidor (${code()})"
    }
    is IOException -> "No se pudo conectar con el servidor. ¿Está corriendo el backend?"
    else -> message ?: "Error inesperado"
}

private fun HttpException.esLogin(): Boolean =
    response()?.raw()?.request?.url?.encodedPath?.endsWith("/auth/login") == true
