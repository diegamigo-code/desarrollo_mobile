package com.example.desarrollo_mobile

import com.example.desarrollo_mobile.model.AccesoDemo
import com.example.desarrollo_mobile.model.Rol
import org.junit.Assert.*
import org.junit.Test

class AccesoDemoTest {
    @Test fun lasCredencialesAbrenSuRol() {
        assertEquals(Rol.EVALUADORA, AccesoDemo.verificar(" Evaluadora ", "Rosa100!")?.rol)
        assertEquals(Rol.DIRECCION, AccesoDemo.verificar("direccion", "Orbita100!")?.rol)
    }

    @Test fun rechazaClaveDeOtroRol() {
        assertNull(AccesoDemo.verificar("direccion", "Rosa100!"))
        assertNull(AccesoDemo.verificar("", ""))
    }
}
