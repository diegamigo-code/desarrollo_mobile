package com.example.desarrollo_mobile.data.local

import com.example.desarrollo_mobile.model.Criterio

object DatosSemilla {

    val CRITERIOS_INICIALES = listOf(
        Criterio(
            id = 1,
            nombre = "Experiencia Técnica y Trayectoria",
            descripcion = "Evaluación de los años de experiencia y proyectos previos en áreas tecnológicas.",
            pesoPorcentaje = 25,
            orden = 1
        ),
        Criterio(
            id = 2,
            nombre = "Conocimientos en Ciberseguridad / TI",
            descripcion = "Dominio de herramientas, certificaciones y fundamentos técnicos del área.",
            pesoPorcentaje = 20,
            orden = 2
        ),
        Criterio(
            id = 3,
            nombre = "Habilidades Blandas y Liderazgo",
            descripcion = "Capacidad de trabajo en equipo, comunicación efectiva y liderazgo de proyectos.",
            pesoPorcentaje = 20,
            orden = 3
        ),
        Criterio(
            id = 4,
            nombre = "Potencial de Crecimiento e Impacto",
            descripcion = "Alineación de la candidata con los objetivos y proyección dentro de DYM Cyber.",
            pesoPorcentaje = 20,
            orden = 4
        ),
        Criterio(
            id = 5,
            nombre = "Calidad de la Postulación y CV",
            descripcion = "Claridad, estructura del perfil de LinkedIn, resumen profesional y CV adjunto.",
            pesoPorcentaje = 15,
            orden = 5
        )
    )
}