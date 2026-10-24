package com.example.desarrollo_mobile.data.local

import androidx.room.TypeConverter
import com.example.desarrollo_mobile.model.EstadoCandidatura

class Converters {
    @TypeConverter
    fun fromEstadoCandidatura(estado: EstadoCandidatura): String {
        return estado.name
    }

    @TypeConverter
    fun toEstadoCandidatura(value: String): EstadoCandidatura {
        return try {
            EstadoCandidatura.valueOf(value)
        } catch (e: Exception) {
            EstadoCandidatura.POSTULADA
        }
    }
}