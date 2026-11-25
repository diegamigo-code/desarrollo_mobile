package com.example.appsemestral.data.remote

import retrofit2.Response
import retrofit2.http.*

/** Microservicio propio wim100-backend. */
interface WimApi {

    @GET("postulantes")
    suspend fun listar(): List<PostulanteDto>

    @GET("postulantes/{id}")
    suspend fun obtener(@Path("id") id: Long): PostulanteDto

    @POST("postulantes")
    suspend fun crear(@Body body: PostulanteDto): PostulanteDto

    @PUT("postulantes/{id}")
    suspend fun actualizar(@Path("id") id: Long, @Body body: PostulanteDto): PostulanteDto

    @PATCH("postulantes/{id}/estado")
    suspend fun cambiarEstado(@Path("id") id: Long, @Body body: EstadoRequest): PostulanteDto

    @DELETE("postulantes/{id}")
    suspend fun eliminar(@Path("id") id: Long): Response<Unit>

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    /** Registra el cierre de sesión en el servidor. Recibe el token explícito porque se limpia antes de llamar. */
    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") autorizacion: String): Response<Unit>

    @GET("auth/me")
    suspend fun perfil(): UsuarioDto
}
