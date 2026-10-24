package com.example.desarrollo_mobile.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "evaluaciones",
    indices = [Index(value = ["candidataId", "evaluadoraClave"], unique = true)]
)
data class Evaluacion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val candidataId: Long,
    val evaluadoraClave: String,
    val evaluadoraNombre: String,
    val puntajeTotal: Double,
    val observacionGeneral: String,
    val fechaCreacion: Long = System.currentTimeMillis()
) {
    companion object {
        const val MINIMO_PARA_CONSOLIDAR = 2
    }
}