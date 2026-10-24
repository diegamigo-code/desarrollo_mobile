package com.example.desarrollo_mobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial")
data class RegistroHistorial(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val candidataId: Long?,
    val usuarioNombre: String,
    val usuarioRol: String,
    val accion: String,
    val detalle: String,
    val fecha: Long = System.currentTimeMillis()
) {
    object Acciones {
        const val REGISTRO_CANDIDATA = "Registro de candidatura"
        const val EVALUACION_CREADA = "Evaluación registrada"
        const val INTENTO_DUPLICADO = "Intento de evaluación duplicada"
        const val CAMBIO_ESTADO = "Cambio de estado"
        const val INICIO_SESION = "Inicio de sesión"
        const val CIERRE_SESION = "Cierre de sesión"
    }
}