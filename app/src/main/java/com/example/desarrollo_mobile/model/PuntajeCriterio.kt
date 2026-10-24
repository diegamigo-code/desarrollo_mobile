package com.example.desarrollo_mobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "puntajes_criterio")
data class PuntajeCriterio(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val evaluacionId: Long,
    val criterioId: Long,
    val puntaje: Int,
    val observacion: String
)

data class DetallePuntaje(
    val criterioNombre: String,
    val pesoPorcentaje: Int,
    val puntaje: Int,
    val observacion: String
)