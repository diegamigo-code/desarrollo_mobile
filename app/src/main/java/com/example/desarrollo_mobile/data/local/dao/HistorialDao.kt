package com.example.desarrollo_mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.desarrollo_mobile.model.RegistroHistorial
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorialDao {

    @Insert
    suspend fun insertar(registro: RegistroHistorial)

    @Query("SELECT * FROM historial ORDER BY fecha DESC")
    fun obtenerTodo(): Flow<List<RegistroHistorial>>

    @Query("SELECT * FROM historial WHERE candidataId = :candidataId ORDER BY fecha DESC")
    fun obtenerPorCandidata(candidataId: Long): Flow<List<RegistroHistorial>>
}