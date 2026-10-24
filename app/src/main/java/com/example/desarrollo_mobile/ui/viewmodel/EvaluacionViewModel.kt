package com.example.desarrollo_mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desarrollo_mobile.data.repository.WimRepository
import com.example.desarrollo_mobile.model.Criterio
import com.example.desarrollo_mobile.validation.EvaluacionValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class EvaluacionFormUiState(
    val puntajes: Map<Long, Int> = emptyMap(),
    val observacionesCriterio: Map<Long, String> = emptyMap(),
    val observacionGeneral: String = "",
    val erroresCriterio: Map<Long, String> = emptyMap(),
    val errorObservacionGeneral: String? = null,
    val errorGeneral: String? = null,
    val estaGuardando: Boolean = false,
    val guardadoExitoso: Boolean = false
)

class EvaluacionViewModel(
    private val repository: WimRepository
) : ViewModel() {

    private val _formState = MutableStateFlow(EvaluacionFormUiState())
    val formState: StateFlow<EvaluacionFormUiState> = _formState.asStateFlow()

    val criterios: StateFlow<List<Criterio>> = repository.criterios
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onPuntajeCambiado(criterioId: Long, puntaje: Int) {
        val mapaActual = _formState.value.puntajes.toMutableMap()
        mapaActual[criterioId] = puntaje
        _formState.value = _formState.value.copy(
            puntajes = mapaActual,
            erroresCriterio = _formState.value.erroresCriterio - criterioId
        )
    }

    fun onObservacionCriterioCambiada(criterioId: Long, observacion: String) {
        val mapaActual = _formState.value.observacionesCriterio.toMutableMap()
        mapaActual[criterioId] = observacion
        _formState.value = _formState.value.copy(
            observacionesCriterio = mapaActual,
            erroresCriterio = _formState.value.erroresCriterio - criterioId
        )
    }

    fun onObservacionGeneralCambiada(observacion: String) {
        _formState.value = _formState.value.copy(
            observacionGeneral = observacion,
            errorObservacionGeneral = null,
            errorGeneral = null
        )
    }

    fun registrarEvaluacion(
        candidataId: Long,
        evaluadoraClave: String,
        evaluadoraNombre: String
    ) {
        val state = _formState.value
        val listaCriterios = criterios.value

        val erroresObs = mutableMapOf<Long, String>()
        var hayErrores = false

        for (criterio in listaCriterios) {
            val nota = state.puntajes[criterio.id] ?: 0
            val obs = state.observacionesCriterio[criterio.id] ?: ""

            if (nota !in 1..5) {
                erroresObs[criterio.id] = "Debe asignar una nota entre 1 y 5"
                hayErrores = true
            } else {
                val resObs = EvaluacionValidator.validarObservacionCriterio(nota, obs)
                if (!resObs.esValido) {
                    erroresObs[criterio.id] = resObs.errorOrNull ?: "Justificación requerida"
                    hayErrores = true
                }
            }
        }

        val resObsGen = EvaluacionValidator.validarObservacionGeneral(state.observacionGeneral)
        val errorGenText = if (!resObsGen.esValido) resObsGen.errorOrNull else null
        if (!resObsGen.esValido) hayErrores = true

        if (hayErrores) {
            _formState.value = state.copy(
                erroresCriterio = erroresObs,
                errorObservacionGeneral = errorGenText
            )
            return
        }

        viewModelScope.launch {
            _formState.value = state.copy(estaGuardando = true)

            val listaParPuntajes = listaCriterios.map { criterio ->
                val nota = state.puntajes[criterio.id] ?: 0
                val obs = state.observacionesCriterio[criterio.id] ?: ""
                Pair(criterio.id, Pair(nota, obs))
            }

            val resultado = repository.registrarEvaluacion(
                candidataId = candidataId,
                evaluadoraClave = evaluadoraClave,
                evaluadoraNombre = evaluadoraNombre,
                puntajesPorCriterio = listaParPuntajes,
                observacionGeneral = state.observacionGeneral
            )

            resultado.fold(
                onSuccess = {
                    _formState.value = EvaluacionFormUiState(guardadoExitoso = true)
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
}