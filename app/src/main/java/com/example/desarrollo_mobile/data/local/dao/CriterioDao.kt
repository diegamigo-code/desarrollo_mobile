package com.example.desarrollo_mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.desarrollo_mobile.model.Criterio
import kotlinx.coroutines.flow.Flow

@Dao
interface CriterioDao {

    @Query("SELECT * FROM criterios ORDER BY orden ASC")
    fun obtenerCriterios(): Flow<List<Criterio>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLote(criterios: List<Criterio>)
}
