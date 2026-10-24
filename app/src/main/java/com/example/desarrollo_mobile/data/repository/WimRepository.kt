package com.example.desarrollo_mobile.data.repository

import com.example.desarrollo_mobile.data.local.dao.CandidataDao
import com.example.desarrollo_mobile.data.local.dao.CriterioDao
import com.example.desarrollo_mobile.data.local.dao.EvaluacionDao
import com.example.desarrollo_mobile.data.local.dao.HistorialDao
import com.example.desarrollo_mobile.model.CalculadoraPuntaje
import com.example.desarrollo_mobile.model.Candidata
import com.example.desarrollo_mobile.model.Criterio
import com.example.desarrollo_mobile.model.DetallePuntaje
import com.example.desarrollo_mobile.model.EstadoCandidatura
import com.example.desarrollo_mobile.model.Evaluacion
import com.example.desarrollo_mobile.model.PuntajeCriterio
import com.example.desarrollo_mobile.model.RegistroHistorial
import com.example.desarrollo_mobile.model.ResultadoConsolidado
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class WimRepository(
    private val candidataDao: CandidataDao,
    private val criterioDao: CriterioDao,
    private val evaluacionDao: EvaluacionDao,
    private val historialDao: HistorialDao
) {

    // --- CANDIDATAS ---
    val candidatas: Flow<List<Candidata>> = candidataDao.obtenerTodas()

    fun obtenerCandidataPorId(id: Long): Flow<Candidata?> = candidataDao.obtenerPorId(id)

    suspend fun registrarCandidata(
        candidata: Candidata,
        usuarioNombre: String,
        usuarioRol: String
    ): Result<Long> {
        return try {
            val existeCorreo = candidataDao.contarPorCorreo(candidata.correo) > 0
            if (existeCorreo) {
                historialDao.insertar(
                    RegistroHistorial(
                        candidataId = null,
                        usuarioNombre = usuarioNombre,
                        usuarioRol = usuarioRol,
                        accion = RegistroHistorial.Acciones.REGISTRO_CANDIDATA,
                        detalle = "Intento fallido de registro. Correo duplicado: ${candidata.correo}"
                    )
                )
                return Result.failure(IllegalArgumentException("El correo ya se encuentra registrado"))
            }

            val idCreado = candidataDao.insertar(candidata)

            historialDao.insertar(
                RegistroHistorial(
                    candidataId = idCreado,
                    usuarioNombre = usuarioNombre,
                    usuarioRol = usuarioRol,
                    accion = RegistroHistorial.Acciones.REGISTRO_CANDIDATA,
                    detalle = "Candidata registrada exitosamente: ${candidata.nombre}"
                )
            )

            Result.success(idCreado)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun actualizarEstadoCandidata(
        candidataId: Long,
        nuevoEstado: EstadoCandidatura,
        usuarioNombre: String,
        usuarioRol: String
    ) {
        candidataDao.actualizarEstado(candidataId, nuevoEstado)
        historialDao.insertar(
            RegistroHistorial(
                candidataId = candidataId,
                usuarioNombre = usuarioNombre,
                usuarioRol = usuarioRol,
                accion = RegistroHistorial.Acciones.CAMBIO_ESTADO,
                detalle = "Estado actualizado a: ${nuevoEstado.etiqueta}"
            )
        )
    }

    // --- CRITERIOS ---
    val criterios: Flow<List<Criterio>> = criterioDao.obtenerCriterios()

    // --- EVALUACIONES ---
    val rankingConsolidado: Flow<List<ResultadoConsolidado>> = evaluacionDao.obtenerRankingConsolidado()

    fun obtenerDetallePuntajes(candidataId: Long): Flow<List<DetallePuntaje>> {
        return evaluacionDao.obtenerDetallePuntajes(candidataId)
    }

    suspend fun registrarEvaluacion(
        candidataId: Long,
        evaluadoraClave: String,
        evaluadoraNombre: String,
        puntajesPorCriterio: List<Pair<Long, Pair<Int, String>>>, // Map: criterioId -> (puntaje, observacion)
        observacionGeneral: String
    ): Result<Long> {
        return try {
            // 1. Validar que la evaluadora no haya evaluado previamente a esta candidata
            val yaEvaluada = evaluacionDao.contarPorEvaluadoraYCandidata(candidataId, evaluadoraClave) > 0
            if (yaEvaluada) {
                historialDao.insertar(
                    RegistroHistorial(
                        candidataId = candidataId,
                        usuarioNombre = evaluadoraNombre,
                        usuarioRol = "Evaluadora",
                        accion = RegistroHistorial.Acciones.INTENTO_DUPLICADO,
                        detalle = "La evaluadora $evaluadoraNombre intentó evaluar nuevamente a la candidata ID $candidataId"
                    )
                )
                return Result.failure(IllegalStateException("Ya has registrado una evaluación para esta candidata"))
            }

            // 2. Obtener los criterios para calcular los pesos de la nota
            val listaCriterios = criterioDao.obtenerCriterios().first()
            val mapaPesos = listaCriterios.associate { it.id to it.pesoPorcentaje }

            val paresPuntajePeso = puntajesPorCriterio.map { (criterioId, parNotaObs) ->
                val peso = mapaPesos[criterioId] ?: 0
                Pair(parNotaObs.first, peso)
            }

            val puntajeTotal = CalculadoraPuntaje.calcularPuntajePonderado(paresPuntajePeso)

            // 3. Insertar Evaluacion
            val nuevaEvaluacion = Evaluacion(
                candidataId = candidataId,
                evaluadoraClave = evaluadoraClave,
                evaluadoraNombre = evaluadoraNombre,
                puntajeTotal = puntajeTotal,
                observacionGeneral = observacionGeneral
            )
            val evaluacionId = evaluacionDao.insertarEvaluacion(nuevaEvaluacion)

            // 4. Insertar los puntajes por criterio
            val listaPuntajesEntity = puntajesPorCriterio.map { (criterioId, parNotaObs) ->
                PuntajeCriterio(
                    evaluacionId = evaluacionId,
                    criterioId = criterioId,
                    puntaje = parNotaObs.first,
                    observacion = parNotaObs.second
                )
            }
            evaluacionDao.insertarPuntajes(listaPuntajesEntity)

            // 5. Trazabilidad
            historialDao.insertar(
                RegistroHistorial(
                    candidataId = candidataId,
                    usuarioNombre = evaluadoraNombre,
                    usuarioRol = "Evaluadora",
                    accion = RegistroHistorial.Acciones.EVALUACION_CREADA,
                    detalle = "Evaluación registrada con nota ${String.format("%.1f", puntajeTotal)}/100"
                )
            )

            // 6. Verificar si la candidata ya cumple el mínimo de evaluaciones para consolidarla
            val candidataActual = candidataDao.obtenerPorId(candidataId).first()
            if (candidataActual?.estado == EstadoCandidatura.POSTULADA) {
                candidataDao.actualizarEstado(candidataId, EstadoCandidatura.EN_EVALUACION)
            }

            Result.success(evaluacionId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- HISTORIAL DE AUDITORÍA ---
    val historialCompleto: Flow<List<RegistroHistorial>> = historialDao.obtenerTodo()

    fun obtenerHistorialCandidata(candidataId: Long): Flow<List<RegistroHistorial>> {
        return historialDao.obtenerPorCandidata(candidataId)
    }

    suspend fun registrarEventoAuditoria(
        usuarioNombre: String,
        usuarioRol: String,
        accion: String,
        detalle: String,
        candidataId: Long? = null
    ) {
        historialDao.insertar(
            RegistroHistorial(
                candidataId = candidataId,
                usuarioNombre = usuarioNombre,
                usuarioRol = usuarioRol,
                accion = accion,
                detalle = detalle
            )
        )
    }
}