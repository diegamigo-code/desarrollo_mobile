package com.example.desarrollo_mobile.data.repository

import com.example.desarrollo_mobile.model.*
import kotlinx.coroutines.flow.Flow

interface RepositorioApp {
    val candidatas: Flow<List<Candidata>>
    val criterios: Flow<List<Criterio>>
    val rankingConsolidado: Flow<List<ResultadoConsolidado>>
    fun obtenerCandidataPorId(id: Long): Flow<Candidata?>
    fun obtenerHistorialCandidata(candidataId: Long): Flow<List<RegistroHistorial>>
    suspend fun login(usuario: String, clave: String): Sesion
    suspend fun logout(sesion: Sesion)
    suspend fun refrescar()
    suspend fun editarCandidata(c: Candidata): Result<Long>
    suspend fun registrarCandidata(candidata: Candidata, usuarioNombre: String, usuarioRol: String): Result<Long>
    suspend fun actualizarEstadoCandidata(candidataId: Long, nuevoEstado: EstadoCandidatura, usuarioNombre: String, usuarioRol: String)
    suspend fun registrarEvaluacion(candidataId: Long, evaluadoraClave: String, evaluadoraNombre: String,
        puntajesPorCriterio: List<Pair<Long, Pair<Int, String>>>, observacionGeneral: String): Result<Long>
}
