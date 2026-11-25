package com.example.desarrollo_mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.desarrollo_mobile.model.DetallePuntaje
import com.example.desarrollo_mobile.model.Evaluacion
import com.example.desarrollo_mobile.model.PuntajeCriterio
import com.example.desarrollo_mobile.model.ResultadoConsolidado
import kotlinx.coroutines.flow.Flow

@Dao
interface EvaluacionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarEvaluacion(evaluacion: Evaluacion): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEvaluacionesLote(evaluaciones: List<Evaluacion>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarPuntajes(puntajes: List<PuntajeCriterio>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPuntajesLote(puntajes: List<PuntajeCriterio>)

    @Query("SELECT COUNT(*) FROM evaluaciones WHERE candidataId = :candidataId AND evaluadoraClave = :evaluadoraClave")
    suspend fun contarPorEvaluadoraYCandidata(candidataId: Long, evaluadoraClave: String): Int

    @Query("""
        SELECT c.nombre AS criterioNombre, c.pesoPorcentaje, p.puntaje, p.observacion
        FROM puntajes_criterio p
        INNER JOIN criterios c ON p.criterioId = c.id
        INNER JOIN evaluaciones e ON p.evaluacionId = e.id
        WHERE e.candidataId = :candidataId
        ORDER BY c.orden ASC
    """)
    fun obtenerDetallePuntajes(candidataId: Long): Flow<List<DetallePuntaje>>

    @Query("""
        SELECT 
            c.id AS candidataId,
            c.nombre AS candidataNombre,
            c.area AS area,
            c.estado AS estado,
            COUNT(e.id) AS cantidadEvaluaciones,
            COALESCE(AVG(e.puntajeTotal), 0.0) AS promedioPuntaje
        FROM candidatas c
        LEFT JOIN evaluaciones e ON c.id = e.candidataId
        GROUP BY c.id
        ORDER BY promedioPuntaje DESC
    """)
    fun obtenerRankingConsolidado(): Flow<List<ResultadoConsolidado>>
}