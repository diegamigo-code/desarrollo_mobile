package com.example.desarrollo_mobile

import com.example.desarrollo_mobile.data.remote.RepositorioSpring
import com.example.desarrollo_mobile.model.Rol
import com.google.gson.JsonParser
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL

class RepositorioSpringTest {
    private class Intercambio(url: URL, val codigo: Int, val respuesta: String) : HttpURLConnection(url) {
        val cuerpo = ByteArrayOutputStream()
        override fun getResponseCode() = codigo
        override fun getOutputStream() = cuerpo
        override fun getInputStream() = respuesta.byteInputStream()
        override fun getErrorStream() = respuesta.byteInputStream()
        override fun getHeaderField(name: String): String? =
            if (url.path.endsWith("/login") && name == "Set-Cookie") "JSESSIONID=demo; HttpOnly" else null
        override fun connect() {}
        override fun disconnect() {}
        override fun usingProxy() = false
    }

    @Test fun usaElRolDelServidorYComparteLaCookieConElPanel() = runBlocking {
        val intercambios = mutableListOf<Intercambio>()
        val repo = RepositorioSpring("http://localhost/api") { url ->
            val json = when (url.path) {
                "/api/auth/login" -> """{"usuario":"prueba","rol":"DIRECCION"}"""
                "/api/panel" -> """{"postulantes":[],"criterios":[],"historial":[],"ranking":[
                    {"candidataId":9,"candidataNombre":"Prueba","area":"TI","estado":"EN_EVALUACION",
                     "cantidadEvaluaciones":1,"promedioPuntaje":70.0}]}"""
                else -> error("Ruta inesperada: ${url.path}")
            }
            Intercambio(url, 200, json).also(intercambios::add)
        }

        assertEquals(Rol.DIRECCION, repo.login("prueba", "clave").rol)
        assertEquals("POST", intercambios.first().requestMethod)
        assertEquals("prueba", JsonParser.parseString(intercambios.first().cuerpo.toString()).asJsonObject["usuario"].asString)
        assertEquals("JSESSIONID=demo", intercambios.last().getRequestProperty("Cookie"))
        assertEquals(70.0, repo.rankingConsolidado.first().single().promedioPuntaje, 0.001)
    }

    @Test fun muestraElConflictoQueDevuelveLaApiAlEvaluar() = runBlocking {
        val repo = RepositorioSpring("http://localhost/api") { url ->
            assertEquals("/api/postulantes/4/evaluaciones", url.path)
            Intercambio(url, 409, """{"mensaje":"Ya evaluaste a esta candidata","errores":{}}""")
        }
        val resultado = repo.registrarEvaluacion(4, "usuario", "nombre",
            (1L..5L).map { it to (4 to "") }, "Cumple con los criterios establecidos.")
        assertEquals("Ya evaluaste a esta candidata", resultado.exceptionOrNull()?.message)
    }
}
