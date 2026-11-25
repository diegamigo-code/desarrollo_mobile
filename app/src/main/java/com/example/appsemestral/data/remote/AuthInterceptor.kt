package com.example.appsemestral.data.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Agrega "Authorization: Bearer <token>" a todas las llamadas al backend,
 * excepto el login. Si el servidor responde 401 a una llamada que llevaba
 * nuestro token, la sesión se da por expirada.
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val esLogin = original.url.encodedPath.endsWith("/auth/login")
        val token = SesionToken.token

        // No se pisa una cabecera puesta a mano (ej. logout) ni se envía token al login
        if (esLogin || token == null || original.header("Authorization") != null) {
            return chain.proceed(original)
        }

        val respuesta = chain.proceed(
            original.newBuilder()
                .header("Authorization", SesionToken.cabecera(token))
                .build()
        )

        // Solo si el token rechazado sigue siendo el actual (evita carreras con un nuevo login)
        if (respuesta.code == 401 && SesionToken.token == token) {
            SesionToken.notificarExpirada()
        }
        return respuesta
    }
}
