package com.example.desarrollo_mobile.data.remote

import com.example.desarrollo_mobile.data.repository.RepositorioApp
import com.example.desarrollo_mobile.model.*
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

private data class Postulante(
    val id: Long, val nombre: String, val correo: String, val telefono: String, val area: String,
    val aniosExperiencia: Int, val resumenExperiencia: String, val linkedinUrl: String, val cvNombre: String?,
    val estado: EstadoCandidatura, val registradaPor: String
) {
    fun candidata() = Candidata(id, nombre, correo, telefono, area, aniosExperiencia,
        resumenExperiencia, linkedinUrl, cvNombre.orEmpty(), estado, registradaPor)
}

private data class Panel(val postulantes: List<Postulante> = emptyList(), val criterios: List<Criterio> = emptyList(),
    val historial: List<RegistroHistorial> = emptyList(), val ranking: List<ResultadoConsolidado> = emptyList())
private data class Cuenta(val usuario: String, val rol: Rol)
private data class ErrorApi(val mensaje: String?, val errores: Map<String, String>?)

class SesionCaducada : IllegalStateException("Tu sesión terminó. Vuelve a iniciar sesión.")

class RepositorioSpring(
    private val baseUrl: String,
    private val abrirConexion: (URL) -> HttpURLConnection = { it.openConnection() as HttpURLConnection }
) : RepositorioApp {
    private val gson = Gson()
    private val mutex = Mutex()
    private var cookie: String? = null
    private val panel = MutableStateFlow(Panel())

    override val candidatas = panel.map { p -> p.postulantes.map { it.candidata() } }
    override val criterios = panel.map { it.criterios }
    override val rankingConsolidado = panel.map { it.ranking }
    override fun obtenerCandidataPorId(id: Long) = candidatas.map { lista -> lista.find { it.id == id } }
    override fun obtenerHistorialCandidata(candidataId: Long) = panel.map { p -> p.historial.filter { it.candidataId == candidataId } }

    private suspend fun solicitar(ruta: String, metodo: String = "GET", datos: Any? = null): String = withContext(Dispatchers.IO) {
        val conexion = abrirConexion(URL("$baseUrl/$ruta"))
        try {
            conexion.requestMethod = metodo
            conexion.instanceFollowRedirects = false
            conexion.connectTimeout = 8000
            conexion.readTimeout = 12000
            cookie?.let { conexion.setRequestProperty("Cookie", it) }
            if (datos != null) {
                conexion.doOutput = true
                conexion.setRequestProperty("Content-Type", "application/json; charset=utf-8")
                conexion.outputStream.bufferedWriter(Charsets.UTF_8).use { it.write(gson.toJson(datos)) }
            }
            val status = conexion.responseCode
            if (status == HttpURLConnection.HTTP_UNAUTHORIZED && ruta != "auth/login") {
                cookie = null
                panel.value = Panel()
                throw SesionCaducada()
            }
            if (status !in 200..299) {
                val texto = conexion.errorStream?.bufferedReader()?.use { it.readText() }
                val error = runCatching { gson.fromJson(texto, ErrorApi::class.java) }.getOrNull()
                throw IllegalStateException(error?.errores?.values?.firstOrNull() ?: error?.mensaje ?: "Error HTTP $status")
            }
            if (ruta == "auth/login") {
                cookie = requireNotNull(conexion.getHeaderField("Set-Cookie")?.substringBefore(';')) {
                    "El servidor no devolvió una sesión"
                }
            }
            conexion.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
        } catch (e: IOException) {
            throw IllegalStateException("No se pudo comunicar con la API de WIM 100", e)
        } finally {
            conexion.disconnect()
        }
    }

    override suspend fun login(usuario: String, clave: String): Sesion = mutex.withLock {
        cookie = null
        panel.value = Panel()
        val json = solicitar("auth/login", "POST", mapOf("usuario" to usuario, "password" to clave))
        val cuenta = gson.fromJson(json, Cuenta::class.java)
        cargarPanel()
        Sesion(cuenta.usuario, cuenta.rol)
    }

    override suspend fun logout(sesion: Sesion) = mutex.withLock {
        try {
            solicitar("auth/logout", "POST")
        } finally {
            cookie = null
            panel.value = Panel()
        }
        Unit
    }

    private suspend fun cargarPanel() {
        panel.value = gson.fromJson(solicitar("panel"), Panel::class.java)
    }

    override suspend fun refrescar() = mutex.withLock { cargarPanel() }

    private fun datos(c: Candidata) = Postulante(c.id, c.nombre, c.correo, c.telefono, c.area,
        c.anosExperiencia, c.resumen, c.enlacePerfil, c.uriCv, c.estado, c.registradaPor)

    // La pantalla de destino vuelve a leer el panel después de una escritura confirmada.
    private suspend fun guardar(operacion: suspend () -> Long): Result<Long> = try {
        Result.success(mutex.withLock { operacion() })
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun registrarCandidata(candidata: Candidata, usuarioNombre: String, usuarioRol: String) = guardar {
        gson.fromJson(solicitar("postulantes", "POST", datos(candidata)), Postulante::class.java).id
    }

    override suspend fun editarCandidata(c: Candidata) = guardar {
        gson.fromJson(solicitar("postulantes/${c.id}", "PUT", datos(c)), Postulante::class.java).id
    }

    override suspend fun actualizarEstadoCandidata(candidataId: Long, nuevoEstado: EstadoCandidatura,
        usuarioNombre: String, usuarioRol: String) = mutex.withLock {
        solicitar("postulantes/$candidataId/estado", "PATCH", mapOf("estado" to nuevoEstado.name))
        cargarPanel()
    }

    override suspend fun registrarEvaluacion(candidataId: Long, evaluadoraClave: String, evaluadoraNombre: String,
        puntajesPorCriterio: List<Pair<Long, Pair<Int, String>>>, observacionGeneral: String) = guardar {
        val notas = puntajesPorCriterio.map { (id, nota) ->
            mapOf("criterioId" to id, "puntaje" to nota.first, "observacion" to nota.second)
        }
        val datos = mapOf("observacionGeneral" to observacionGeneral, "notas" to notas)
        solicitar("postulantes/$candidataId/evaluaciones", "POST", datos).trim().toLong()
    }
}
