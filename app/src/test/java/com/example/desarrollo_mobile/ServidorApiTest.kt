package com.example.desarrollo_mobile

import com.example.desarrollo_mobile.data.remote.ServidorApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ServidorApiTest {
    private class Respuesta(url: URL, val codigo: Int, val cuerpo: String) : HttpURLConnection(url) {
        override fun getResponseCode() = codigo
        override fun getInputStream() = cuerpo.byteInputStream()
        override fun connect() {}
        override fun disconnect() {}
        override fun usingProxy() = false
    }

    @Test fun usaLaUrlPublicadaEnConfigJson() = runBlocking {
        val servidor = ServidorApi("https://ejemplo.github.io/app/config.json", "http://10.0.2.2:8080/api") { url ->
            Respuesta(url, 200, """{"apiUrl":"https://tunel-demo.trycloudflare.com/api/","actualizado":"2026-11-25T15:00:00Z"}""")
        }
        assertEquals("https://tunel-demo.trycloudflare.com/api", servidor.url())
    }

    @Test fun usaLaUrlPorDefectoSiNoHayConfiguracion() = runBlocking {
        val servidor = ServidorApi("https://ejemplo.github.io/app/config.json", "http://10.0.2.2:8080/api/") { _ ->
            throw IOException("sin red")
        }
        assertEquals("http://10.0.2.2:8080/api", servidor.url())
    }

    @Test fun ignoraUnaConfiguracionInvalida() = runBlocking {
        val servidor = ServidorApi("https://ejemplo.github.io/app/config.json", "http://10.0.2.2:8080/api") { url ->
            Respuesta(url, 200, """{"apiUrl":"no-es-una-url"}""")
        }
        assertEquals("http://10.0.2.2:8080/api", servidor.url())
    }
}
