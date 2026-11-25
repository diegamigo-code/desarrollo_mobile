package com.example.appsemestral.data.local

import com.example.appsemestral.di.AppContainer
import com.example.appsemestral.model.Criterio

/**
 * Precarga la base de datos con datos 100 % SINTÉTICOS para demostrar el flujo completo.
 * Ningún nombre, correo, teléfono, CV ni perfil corresponde a una persona real.
 */
object DatosSemilla {

    /** Matriz WIM provisional (sujeta a validación del Socio Formador). Pesos suman 100. */
    val criterios = listOf(
        Criterio(nombre = "Trayectoria técnica", descripcion = "Experiencia y profundidad técnica en ciberseguridad.", peso = 25, orden = 1),
        Criterio(nombre = "Liderazgo e influencia", descripcion = "Capacidad de liderar equipos, proyectos o iniciativas.", peso = 20, orden = 2),
        Criterio(nombre = "Aporte a la comunidad", descripcion = "Mentorías, charlas, voluntariado y promoción de mujeres en el área.", peso = 20, orden = 3),
        Criterio(nombre = "Innovación y logros", descripcion = "Logros destacados, investigación, certificaciones o reconocimientos.", peso = 20, orden = 4),
        Criterio(nombre = "Comunicación y visibilidad", descripcion = "Claridad para comunicar y presencia en el ecosistema.", peso = 15, orden = 5)
    )

    suspend fun poblarCriterios(container: AppContainer) {
        container.evaluacionRepository.asegurarCriterios(criterios)
    }
}
