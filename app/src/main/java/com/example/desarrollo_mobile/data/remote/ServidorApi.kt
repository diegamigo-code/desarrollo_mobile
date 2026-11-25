package com.example.desarrollo_mobile.data.remote

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

private data class ConfiguracionRemota(val apiUrl: String?)

/**
 * Obtiene la URL vigente de la API desde un config.json publicado en GitHub Pages.
 * El script publicar_backend.bat del backend la actualiza cada vez que abre un túnel de Cloudflare,
 * así los teléfonos no necesitan reinstalar la app cuando cambia la dirección.
 * Si no se puede leer, usa la URL compilada en la app.
 */
class ServidorApi(
    private val configUrl: String,
    private val urlPorDefecto: String,
    private val abrirConexion: (URL) -> HttpURLConnection = { it.openConnection() as HttpURLConnection }
) {
    private val gson = Gson()

    @Volatile
    private var actual: String? = null

    suspend fun url(): String = actual ?: actualizar()

    suspend fun actualizar(): String = withContext(Dispatchers.IO) {
        val nueva = runCatching { leerConfiguracion() }.getOrNull() ?: urlPorDefecto.trimEnd('/')
        actual = nueva
        nueva
    }

    private fun leerConfiguracion(): String? {
        if (configUrl.isBlank()) return null
        val conexion = abrirConexion(URL("$configUrl?t=${System.currentTimeMillis()}"))
        try {
            conexion.connectTimeout = 5000
            conexion.readTimeout = 5000
            conexion.useCaches = false
            if (conexion.responseCode !in 200..299) return null
            val json = conexion.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
            val url = gson.fromJson(json, ConfiguracionRemota::class.java)?.apiUrl?.trim()?.trimEnd('/')
            return url?.takeIf { it.startsWith("https://") || it.startsWith("http://") }
        } finally {
            conexion.disconnect()
        }
    }
}
