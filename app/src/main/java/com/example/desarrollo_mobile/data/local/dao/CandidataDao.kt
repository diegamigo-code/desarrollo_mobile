package com.example.desarrollo_mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.desarrollo_mobile.model.Candidata
import com.example.desarrollo_mobile.model.EstadoCandidatura
import kotlinx.coroutines.flow.Flow

@Dao
interface CandidataDao {

    @Query("SELECT * FROM candidatas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<Candidata>>

    @Query("SELECT * FROM candidatas WHERE id = :id")
    fun obtenerPorId(id: Long): Flow<Candidata?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(candidata: Candidata): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLote(candidatas: List<Candidata>)

    @Query("UPDATE candidatas SET estado = :nuevoEstado WHERE id = :id")
    suspend fun actualizarEstado(id: Long, nuevoEstado: EstadoCandidatura)

    @Query("SELECT COUNT(*) FROM candidatas")
    suspend fun contar(): Int

    @Query("SELECT COUNT(*) FROM candidatas WHERE LOWER(correo) = LOWER(:correo)")
    suspend fun contarPorCorreo(correo: String): Int
}