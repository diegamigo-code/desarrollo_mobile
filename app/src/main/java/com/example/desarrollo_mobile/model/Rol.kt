package com.example.desarrollo_mobile.model

enum class Rol(val etiqueta: String) {
    EVALUADORA("Evaluadora"),
    DIRECCION("Dirección")
}

data class Sesion(
    val nombreUsuario: String,
    val rol: Rol
)