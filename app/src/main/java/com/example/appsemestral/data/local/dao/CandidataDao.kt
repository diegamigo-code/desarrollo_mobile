package com.example.appsemestral.data.local.dao

import androidx.room.Upsert
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appsemestral.model.Candidata
import com.example.appsemestral.model.EstadoCandidatura
import kotlinx.coroutines.flow.Flow

@Dao
interface CandidataDao {
    @Upsert
    suspend fun upsert(candidata: Candidata)

    @Upsert
    suspend fun upsertTodas(candidatas: List<Candidata>)

    @Query("SELECT * FROM candidatas")
    suspend fun obtenerTodas(): List<Candidata>

    @Query("DELETE FROM candidatas WHERE id = :id")
    suspend fun eliminar(id: Long)

    @Query("DELETE FROM candidatas WHERE id NOT IN (:ids)")
    suspend fun eliminarNoIncluidas(ids: List<Long>)

    @Query("DELETE FROM candidatas")
    suspend fun eliminarTodas()


    @Query("SELECT * FROM candidatas ORDER BY fechaRegistro DESC")
    fun observarTodas(): Flow<List<Candidata>>

    @Query("SELECT * FROM candidatas WHERE id = :id")
    fun observarPorId(id: Long): Flow<Candidata?>

    @Query("SELECT * FROM candidatas WHERE id = :id")
    suspend fun obtenerPorId(id: Long): Candidata?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(candidata: Candidata): Long

    @Query("UPDATE candidatas SET estado = :estado WHERE id = :id")
    suspend fun actualizarEstado(id: Long, estado: EstadoCandidatura)

    @Query("SELECT COUNT(*) FROM candidatas")
    suspend fun contar(): Int

    @Query("SELECT COUNT(*) FROM candidatas WHERE LOWER(correo) = LOWER(:correo)")
    suspend fun contarPorCorreo(correo: String): Int
}
