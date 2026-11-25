package com.example.desarrollo_mobile.model

/** Cuentas públicas de demostración local. */
enum class AccesoDemo(val usuario: String, val clave: String, val rol: Rol) {
    Evaluadora("evaluadora", "Rosa100!", Rol.EVALUADORA),
    Direccion("direccion", "Orbita100!", Rol.DIRECCION);

    companion object {
        fun verificar(usuario: String, clave: String) = entries.firstOrNull {
            it.usuario.equals(usuario.trim(), ignoreCase = true) && it.clave == clave
        }
    }
}
