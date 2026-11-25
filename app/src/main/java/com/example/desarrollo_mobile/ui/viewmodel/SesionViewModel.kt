package com.example.desarrollo_mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desarrollo_mobile.data.repository.RepositorioApp
import com.example.desarrollo_mobile.model.Sesion
import com.example.desarrollo_mobile.data.remote.SesionCaducada
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SesionUiState(
    val sesionActual: Sesion? = null,
    val ocupada: Boolean = false,
    val error: String? = null
) {
    val estaAutenticado get() = sesionActual != null
}

class SesionViewModel(private val repository: RepositorioApp) : ViewModel() {
    private val _uiState = MutableStateFlow(SesionUiState())
    val uiState = _uiState.asStateFlow()

    fun iniciarSesion(usuario: String, clave: String) {
        if (_uiState.value.ocupada) return
        _uiState.value = SesionUiState(ocupada = true)
        viewModelScope.launch {
            try {
                _uiState.value = SesionUiState(repository.login(usuario, clave))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = SesionUiState(error = e.message)
            }
        }
    }

    suspend fun refrescar() {
        val sesion = _uiState.value.sesionActual ?: return
        try {
            repository.refrescar()
            if (_uiState.value.sesionActual == sesion) _uiState.value = SesionUiState(sesion)
        } catch (e: CancellationException) {
            throw e
        } catch (e: SesionCaducada) {
            if (_uiState.value.sesionActual == sesion) _uiState.value = SesionUiState(error = e.message)
        } catch (e: Exception) {
            if (_uiState.value.sesionActual == sesion) _uiState.value = SesionUiState(sesion, error = e.message)
        }
    }

    fun cerrarSesion() {
        val sesion = _uiState.value.sesionActual ?: return
        _uiState.value = SesionUiState(ocupada = true)
        viewModelScope.launch {
            try {
                repository.logout(sesion)
                _uiState.value = SesionUiState()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = SesionUiState(error = e.message)
            }
        }
    }
}
