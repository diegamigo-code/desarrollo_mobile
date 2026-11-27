package com.example.desarrollo_mobile

import com.example.desarrollo_mobile.data.remote.RepositorioSpring
import com.example.desarrollo_mobile.data.remote.ServidorApi
import com.example.desarrollo_mobile.model.Rol
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL

class CambioDeTunelTest {
    private class Respuesta(url: URL, val codigo: Int, val cuerpo: String) : HttpURLConnection(url) {
        override fun getResponseCode() = codigo
        override fun getInputStream() = cuerpo.byteInputStream()
        override fun getErrorStream() = cuerpo.byteInputStream()
        override fun getOutputStream() = java.io.ByteArrayOutputStream()
        override fun getHeaderField(name: String): String? =
            if (url.path.endsWith("/login") && name == "Set-Cookie") "JSESSIONID=demo; HttpOnly" else null
        override fun connect() {}
        override fun disconnect() {}
        override fun usingProxy() = false
    }

    @Test fun siElTunelViejoResponde530SeUsaLaUrlNueva() = runBlocking {
        var lecturasConfig = 0
        val servidor = ServidorApi("https://config.test/config.json", "http://10.0.2.2:8080/api") { url ->
            lecturasConfig++
            val api = if (lecturasConfig == 1) "https://viejo.trycloudflare.com/api" else "https://nuevo.trycloudflare.com/api"
            Respuesta(url, 200, """{"apiUrl":"$api"}""")
        }
        val hosts = mutableListOf<String>()
        val repo = RepositorioSpring("http://10.0.2.2:8080/api", servidor) { url ->
            hosts += url.host
            when {
                url.host == "viejo.trycloudflare.com" -> Respuesta(url, 530, "error code: 1033")
                url.path == "/api/auth/login" -> Respuesta(url, 200, """{"usuario":"direccion","rol":"DIRECCION"}""")
                url.path == "/api/panel" -> Respuesta(url, 200, """{"postulantes":[],"criterios":[],"historial":[],"ranking":[]}""")
                else -> error("Ruta inesperada: $url")
            }
        }

        assertEquals(Rol.DIRECCION, repo.login("direccion", "clave").rol)
        assertEquals(listOf("viejo.trycloudflare.com", "nuevo.trycloudflare.com", "nuevo.trycloudflare.com"), hosts)
        assertEquals(2, lecturasConfig)
    }
}
