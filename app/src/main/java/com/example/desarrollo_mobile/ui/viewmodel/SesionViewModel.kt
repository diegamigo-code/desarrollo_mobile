package com.example.desarrollo_mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desarrollo_mobile.data.repository.WimRepository
import com.example.desarrollo_mobile.model.RegistroHistorial
import com.example.desarrollo_mobile.model.Rol
import com.example.desarrollo_mobile.model.Sesion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SesionUiState(
    val sesionActual: Sesion? = null,
    val estaAutenticado: Boolean = false
)

class SesionViewModel(
    private val repository: WimRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SesionUiState())
    val uiState: StateFlow<SesionUiState> = _uiState.asStateFlow()

    fun iniciarSesion(nombreUsuario: String, rol: Rol) {
        val nuevaSesion = Sesion(nombreUsuario = nombreUsuario, rol = rol)
        _uiState.value = SesionUiState(
            sesionActual = nuevaSesion,
            estaAutenticado = true
        )

        viewModelScope.launch {
            repository.registrarEventoAuditoria(
                usuarioNombre = nombreUsuario,
                usuarioRol = rol.etiqueta,
                accion = RegistroHistorial.Acciones.INICIO_SESION,
                detalle = "Sesión iniciada con rol: ${rol.etiqueta}"
            )
        }
    }

    fun cerrarSesion() {
        val sesionAnterior = _uiState.value.sesionActual
        _uiState.value = SesionUiState()

        sesionAnterior?.let {
            viewModelScope.launch {
                repository.registrarEventoAuditoria(
                    usuarioNombre = it.nombreUsuario,
                    usuarioRol = it.rol.etiqueta,
                    accion = RegistroHistorial.Acciones.CIERRE_SESION,
                    detalle = "Sesión cerrada"
                )
            }
        }
    }
}