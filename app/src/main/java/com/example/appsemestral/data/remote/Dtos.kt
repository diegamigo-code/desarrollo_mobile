package com.example.appsemestral.data.remote

/** JSON de un postulante tal como lo envía y recibe el backend. */
data class PostulanteDto(
    val id: Long? = null,
    val nombre: String,
    val correo: String,
    val telefono: String,
    val area: String,
    val aniosExperiencia: Int,
    val resumenExperiencia: String,
    val linkedinUrl: String,
    val cvNombre: String? = null,
    val estado: String? = null,
    val fechaRegistro: Long? = null,
    val registradaPor: String
)

data class EstadoRequest(val estado: String)

// ---- Autenticación (POST /api/auth/login del backend con Spring Security + JWT) ----
data class LoginRequest(val username: String, val password: String)

/** tipo = "Bearer"; expiraEn en ISO-8601. El token se envía luego en la cabecera Authorization. */
data class LoginResponse(
    val token: String,
    val tipo: String,
    val expiraEn: String?,
    val usuario: UsuarioDto
)

data class UsuarioDto(
    val id: Long,
    val username: String,
    val nombreCompleto: String,
    val rol: String,
    val rolEtiqueta: String?,
    val activo: Boolean
)

// ---- API externa: randomuser.me ----
data class RandomUserResponse(val results: List<RandomUser>)
data class RandomUser(val name: RandomName)
data class RandomName(val first: String, val last: String)
