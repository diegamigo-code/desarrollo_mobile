package com.example.desarrollo_mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desarrollo_mobile.data.repository.WimRepository
import com.example.desarrollo_mobile.model.Candidata
import com.example.desarrollo_mobile.model.EstadoCandidatura
import com.example.desarrollo_mobile.model.ResultadoConsolidado
import com.example.desarrollo_mobile.validation.CandidataValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CandidataFormUiState(
    val nombre: String = "",
    val correo: String = "",
    val telefono: String = "",
    val area: String = Candidata.AREAS.first(),
    val anosExperiencia: String = "",
    val resumen: String = "",
    val enlacePerfil: String = "",
    val uriCv: String = "",
    val errorNombre: String? = null,
    val errorCorreo: String? = null,
    val errorTelefono: String? = null,
    val errorAnos: String? = null,
    val errorResumen: String? = null,
    val errorEnlace: String? = null,
    val errorCv: String? = null,
    val errorGeneral: String? = null,
    val estaGuardando: Boolean = false,
    val guardadoExitoso: Boolean = false
)

class CandidatasViewModel(
    private val repository: WimRepository
) : ViewModel() {

    private val _formState = MutableStateFlow(CandidataFormUiState())
    val formState: StateFlow<CandidataFormUiState> = _formState.asStateFlow()

    private val _busquedaQuery = MutableStateFlow("")
    val busquedaQuery: StateFlow<String> = _busquedaQuery.asStateFlow()

    val candidatasFiltradas: StateFlow<List<Candidata>> = combine(
        repository.candidatas,
        _busquedaQuery
    ) { lista, query ->
        if (query.isBlank()) lista
        else lista.filter {
            it.nombre.contains(query, ignoreCase = true) ||
                    it.area.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val rankingConsolidado: StateFlow<List<ResultadoConsolidado>> = repository.rankingConsolidado
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onBusquedaCambiada(query: String) {
        _busquedaQuery.value = query
    }

    fun onFormCambiado(
        nombre: String = _formState.value.nombre,
        correo: String = _formState.value.correo,
        telefono: String = _formState.value.telefono,
        area: String = _formState.value.area,
        anosExperiencia: String = _formState.value.anosExperiencia,
        resumen: String = _formState.value.resumen,
        enlacePerfil: String = _formState.value.enlacePerfil,
        uriCv: String = _formState.value.uriCv
    ) {
        _formState.value = _formState.value.copy(
            nombre = nombre,
            correo = correo,
            telefono = telefono,
            area = area,
            anosExperiencia = anosExperiencia,
            resumen = resumen,
            enlacePerfil = enlacePerfil,
            uriCv = uriCv,
            errorNombre = null,
            errorCorreo = null,
            errorTelefono = null,
            errorAnos = null,
            errorResumen = null,
            errorEnlace = null,
            errorCv = null,
            errorGeneral = null
        )
    }

    fun registrarCandidata(usuarioNombre: String, usuarioRol: String) {
        val state = _formState.value
        val resNombre = CandidataValidator.validarNombre(state.nombre)
        val resCorreo = CandidataValidator.validarCorreo(state.correo)
        val resTelefono = CandidataValidator.validarTelefono(state.telefono)
        val resAnos = CandidataValidator.validarAnosExperiencia(state.anosExperiencia)
        val resResumen = CandidataValidator.validarResumen(state.resumen)
        val resEnlace = CandidataValidator.validarEnlacePerfil(state.enlacePerfil)
        val resCv = CandidataValidator.validarCvUri(state.uriCv)

        if (!resNombre.esValido || !resCorreo.esValido || !resTelefono.esValido ||
            !resAnos.esValido || !resResumen.esValido || !resEnlace.esValido || !resCv.esValido
        ) {
            _formState.value = state.copy(
                errorNombre = resNombre.errorOrNull,
                errorCorreo = resCorreo.errorOrNull,
                errorTelefono = resTelefono.errorOrNull,
                errorAnos = resAnos.errorOrNull,
                errorResumen = resResumen.errorOrNull,
                errorEnlace = resEnlace.errorOrNull,
                errorCv = resCv.errorOrNull
            )
            return
        }

        viewModelScope.launch {
            _formState.value = state.copy(estaGuardando = true)
            val nuevaCandidata = Candidata(
                nombre = state.nombre.trim(),
                correo = state.correo.trim().lowercase(),
                telefono = state.telefono.trim(),
                area = state.area,
                anosExperiencia = state.anosExperiencia.toInt(),
                resumen = state.resumen.trim(),
                enlacePerfil = state.enlacePerfil.trim(),
                uriCv = state.uriCv,
                registradaPor = usuarioNombre
            )

            val resultado = repository.registrarCandidata(nuevaCandidata, usuarioNombre, usuarioRol)
            resultado.fold(
                onSuccess = {
                    _formState.value = CandidataFormUiState(guardadoExitoso = true)
                },
                onFailure = { ex ->
                    _formState.value = state.copy(
                        estaGuardando = false,
                        errorGeneral = ex.message
                    )
                }
            )
        }
    }

    fun cambiarEstadoCandidata(
        candidataId: Long,
        nuevoEstado: EstadoCandidatura,
        usuarioNombre: String,
        usuarioRol: String
    ) {
        viewModelScope.launch {
            repository.actualizarEstadoCandidata(candidataId, nuevoEstado, usuarioNombre, usuarioRol)
        }
    }
}