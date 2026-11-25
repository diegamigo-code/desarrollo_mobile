package com.example.appsemestral.data.remote

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Guarda el JWT de la sesión actual (solo en memoria: al cerrar la app hay que
 * volver a ingresar, igual que la Sesion del SesionViewModel).
 *
 * AuthInterceptor lo lee para agregar la cabecera Authorization y avisa por
 * [expirada] cuando el servidor rechaza el token (401).
 */
object SesionToken {

    @Volatile
    var token: String? = null
        private set

    /** Fecha de expiración tal como la envía el backend (ISO-8601, ej. 2026-11-25T22:00:00Z). */
    @Volatile
    var expiraEn: String? = null
        private set

    private val _expirada = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val expirada: SharedFlow<Unit> = _expirada.asSharedFlow()

    val activa: Boolean get() = token != null

    fun guardar(token: String, expiraEn: String?) {
        this.token = token
        this.expiraEn = expiraEn
    }

    fun limpiar() {
        token = null
        expiraEn = null
    }

    /** El servidor rechazó el token (expiró, se desactivó la cuenta o cambió la clave del servidor). */
    internal fun notificarExpirada() {
        limpiar()
        _expirada.tryEmit(Unit)
    }

    fun cabecera(token: String) = "Bearer $token"
}
