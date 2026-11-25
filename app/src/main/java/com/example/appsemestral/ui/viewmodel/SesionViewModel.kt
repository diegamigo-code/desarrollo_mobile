package com.example.appsemestral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsemestral.data.remote.LoginRequest
import com.example.appsemestral.data.remote.SesionToken
import com.example.appsemestral.data.remote.WimApi
import com.example.appsemestral.data.remote.mensajeAmigable
import com.example.appsemestral.data.repository.HistorialRepository
import com.example.appsemestral.model.Acciones
import com.example.appsemestral.model.Rol
import com.example.appsemestral.model.Sesion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val usuario: String = "",
    val password: String = "",
    val error: String? = null,
    val intentosFallidos: Int = 0,
    val ingresando: Boolean = false
)

class SesionViewModel(
    private val api: WimApi,
    private val historial: HistorialRepository
) : ViewModel() {
    private val _login = MutableStateFlow(LoginUiState())
    val loginState = _login.asStateFlow()
    private val _sesion = MutableStateFlow<Sesion?>(null)
    val sesion = _sesion.asStateFlow()

    /** Emite cuando el servidor rechaza el token: la UI vuelve al login y muestra el aviso. */
    private val _sesionExpirada = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val sesionExpirada = _sesionExpirada.asSharedFlow()

    init {
        viewModelScope.launch {
            SesionToken.expirada.collect {
                val actual = _sesion.value ?: return@collect
                _sesion.value = null
                _login.value = LoginUiState(error = "Tu sesión expiró. Vuelve a ingresar")
                _sesionExpirada.tryEmit(Unit)
                historial.registrar(null, Acciones.CIERRE_SESION, "Sesión expirada (token rechazado por el servidor)", actual.nombre)
            }
        }
    }

    fun onUsuario(valor: String) = _login.update { it.copy(usuario = valor, error = null) }
    fun onPassword(valor: String) = _login.update { it.copy(password = valor, error = null) }

    fun validar(): Boolean {
        val estado = _login.value
        if (estado.ingresando) return false
        if (estado.usuario.isNotBlank() && estado.password.isNotEmpty()) return true
        _login.update {
            it.copy(error = "Ingresa tu usuario y contraseña", intentosFallidos = it.intentosFallidos + 1)
        }
        return false
    }

    fun iniciarSesion(metodo: String, onIngreso: () -> Unit) {
        if (!validar()) return
        val estado = _login.value
        _login.update { it.copy(ingresando = true, error = null) }
        viewModelScope.launch {
            val nueva = try {
                val respuesta = api.login(LoginRequest(estado.usuario.trim(), estado.password))
                val rol = Rol.valueOf(respuesta.usuario.rol)
                SesionToken.guardar(respuesta.token, respuesta.expiraEn)
                Sesion(respuesta.usuario.nombreCompleto, rol)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _login.update {
                    it.copy(ingresando = false, error = e.mensajeAmigable(), intentosFallidos = it.intentosFallidos + 1)
                }
                return@launch
            }
            _login.value = LoginUiState()
            _sesion.value = nueva
            onIngreso()
            historial.registrar(null, Acciones.INICIO_SESION, "Ingreso como ${nueva.rol.etiqueta} ($metodo)", nueva.nombre)
        }
    }

    fun cerrarSesion() {
        val actual = _sesion.value ?: return
        val token = SesionToken.token
        // Se descarta el token antes de avisar al servidor: ninguna otra llamada lo vuelve a usar
        SesionToken.limpiar()
        _sesion.value = null
        _login.value = LoginUiState()
        viewModelScope.launch {
            if (token != null) {
                // Solo auditoría en el backend; si falla (sin red, token vencido) la sesión local igual se cierra
                runCatching { api.logout(SesionToken.cabecera(token)) }
                    .onFailure { if (it is CancellationException) throw it }
            }
            historial.registrar(null, Acciones.CIERRE_SESION, "Salida del perfil ${actual.rol.etiqueta}", actual.nombre)
        }
    }
}
