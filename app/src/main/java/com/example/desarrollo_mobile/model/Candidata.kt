package com.example.desarrollo_mobile.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "candidatas",
    indices = [Index(value = ["correo"], unique = true)]
)
data class Candidata(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val correo: String,
    val telefono: String,
    val area: String,
    val anosExperiencia: Int,
    val resumen: String,
    val enlacePerfil: String,
    val uriCv: String,
    val estado: EstadoCandidatura = EstadoCandidatura.POSTULADA,
    val registradaPor: String
) {
    companion object {
        val AREAS = listOf(
            "Ciberseguridad",
            "Desarrollo de Software",
            "Infraestructura y TI",
            "Datos e IA",
            "Gestión de Proyectos",
            "Investigación y I+D"
        )
    }
}