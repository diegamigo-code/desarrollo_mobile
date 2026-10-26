package com.example.desarrollo_mobile.data.local

import com.example.desarrollo_mobile.model.Candidata
import com.example.desarrollo_mobile.model.Criterio
import com.example.desarrollo_mobile.model.EstadoCandidatura
import com.example.desarrollo_mobile.model.Evaluacion
import com.example.desarrollo_mobile.model.PuntajeCriterio
import com.example.desarrollo_mobile.model.RegistroHistorial

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

    val CANDIDATAS_INICIALES = listOf(
        Candidata(
            id = 1,
            nombre = "Camila Morales",
            correo = "camila.morales@example.com",
            telefono = "+56 9 1234 5678",
            area = "Ciberseguridad",
            anosExperiencia = 6,
            resumen = "Especialista en Ethical Hacking y respuesta a incidentes. Certificada CEH y CISSP. Lideró equipos de SOC en la industria bancaria.",
            enlacePerfil = "https://linkedin.com/in/camila-morales-cyber",
            uriCv = "cv_camila_morales.pdf",
            estado = EstadoCandidatura.EN_EVALUACION,
            registradaPor = "Sistema"
        ),
        Candidata(
            id = 2,
            nombre = "Valentina Silva",
            correo = "valentina.silva@example.com",
            telefono = "+56 9 8765 4321",
            area = "Desarrollo de Software",
            anosExperiencia = 4,
            resumen = "Ingeniera de Software Full Stack enfocada en Kotlin, Android y microservicios AWS. Apasionada por la arquitectura limpia.",
            enlacePerfil = "https://linkedin.com/in/valentina-silva-dev",
            uriCv = "cv_valentina_silva.pdf",
            estado = EstadoCandidatura.EVALUADA,
            registradaPor = "Sistema"
        ),
        Candidata(
            id = 3,
            nombre = "Sofía Araya",
            correo = "sofia.araya@example.com",
            telefono = "+56 9 5555 4433",
            area = "Datos e IA",
            anosExperiencia = 5,
            resumen = "Científica de Datos experta en Machine Learning, MLOps y NLP. Diseñó e implementó modelos predictivos de riesgo crediticio.",
            enlacePerfil = "https://linkedin.com/in/sofia-araya-data",
            uriCv = "cv_sofia_araya.pdf",
            estado = EstadoCandidatura.POSTULADA,
            registradaPor = "Sistema"
        ),
        Candidata(
            id = 4,
            nombre = "Mariana Fuentes",
            correo = "mariana.fuentes@example.com",
            telefono = "+56 9 3344 5566",
            area = "Infraestructura y TI",
            anosExperiencia = 8,
            resumen = "Arquitecta Cloud y DevOps Senior. Experta en Kubernetes, Terraform, CI/CD y migración de infraestructura tradicional.",
            enlacePerfil = "https://linkedin.com/in/mariana-fuentes-cloud",
            uriCv = "cv_mariana_fuentes.pdf",
            estado = EstadoCandidatura.SELECCIONADA,
            registradaPor = "Sistema"
        ),
        Candidata(
            id = 5,
            nombre = "Isadora Rojas",
            correo = "isadora.rojas@example.com",
            telefono = "+56 9 7788 9900",
            area = "Gestión de Proyectos",
            anosExperiencia = 7,
            resumen = "Project Manager certificada PMP y Agile Coach. Lideró la transformación digital de múltiples proyectos en tecnología.",
            enlacePerfil = "https://linkedin.com/in/isadora-rojas-pm",
            uriCv = "cv_isadora_rojas.pdf",
            estado = EstadoCandidatura.POSTULADA,
            registradaPor = "Sistema"
        ),
        Candidata(
            id = 6,
            nombre = "Gabriela Castro",
            correo = "gabriela.castro@example.com",
            telefono = "+56 9 6677 8899",
            area = "Investigación y I+D",
            anosExperiencia = 3,
            resumen = "Investigadora en Criptografía Avanzada. MSc en Ciencias de la Computación con publicaciones en conferencias internacionales.",
            enlacePerfil = "https://linkedin.com/in/gabriela-castro-research",
            uriCv = "cv_gabriela_castro.pdf",
            estado = EstadoCandidatura.EN_EVALUACION,
            registradaPor = "Sistema"
        )
    )

    val EVALUACIONES_INICIALES = listOf(
        Evaluacion(
            id = 1,
            candidataId = 1,
            evaluadoraClave = "andrea_torres",
            evaluadoraNombre = "Andrea Torres",
            puntajeTotal = 91.25,
            observacionGeneral = "Excelente perfil técnico en Ciberseguridad con sólida experiencia en SOC."
        ),
        Evaluacion(
            id = 2,
            candidataId = 2,
            evaluadoraClave = "maria_fernandez",
            evaluadoraNombre = "María Fernández",
            puntajeTotal = 91.50,
            observacionGeneral = "Perfil muy sólido en Desarrollo Mobile y Clean Architecture."
        ),
        Evaluacion(
            id = 3,
            candidataId = 4,
            evaluadoraClave = "maria_fernandez",
            evaluadoraNombre = "María Fernández",
            puntajeTotal = 94.75,
            observacionGeneral = "Excelente candidata para el rol de Infraestructura Cloud. Cumple todos los requisitos."
        ),
        Evaluacion(
            id = 4,
            candidataId = 4,
            evaluadoraClave = "andrea_torres",
            evaluadoraNombre = "Andrea Torres",
            puntajeTotal = 92.05,
            observacionGeneral = "Gran capacidad de liderazgo y vasto dominio técnico DevOps."
        )
    )

    val PUNTAJES_INICIALES = listOf(
        // Evaluacion 1 (Camila Morales - Andrea Torres)
        PuntajeCriterio(id = 1, evaluacionId = 1, criterioId = 1, puntaje = 95, observacion = "Sólida trayectoria en pentesting y respuesta a incidentes."),
        PuntajeCriterio(id = 2, evaluacionId = 1, criterioId = 2, puntaje = 95, observacion = "Certificaciones vigentes CEH y CISSP."),
        PuntajeCriterio(id = 3, evaluacionId = 1, criterioId = 3, puntaje = 85, observacion = "Buena comunicación y trabajo en equipo."),
        PuntajeCriterio(id = 4, evaluacionId = 1, criterioId = 4, puntaje = 90, observacion = "Gran potencial de crecimiento en la organización."),
        PuntajeCriterio(id = 5, evaluacionId = 1, criterioId = 5, puntaje = 90, observacion = "CV claro y perfil de LinkedIn actualizado."),

        // Evaluacion 2 (Valentina Silva - Maria Fernandez)
        PuntajeCriterio(id = 6, evaluacionId = 2, criterioId = 1, puntaje = 90, observacion = "Experiencia sólida en desarrollo nativo Android."),
        PuntajeCriterio(id = 7, evaluacionId = 2, criterioId = 2, puntaje = 85, observacion = "Buen manejo de seguridad a nivel de software."),
        PuntajeCriterio(id = 8, evaluacionId = 2, criterioId = 3, puntaje = 95, observacion = "Excelentes habilidades comunicativas."),
        PuntajeCriterio(id = 9, evaluacionId = 2, criterioId = 4, puntaje = 90, observacion = "Proactiva y orientada a resultados."),
        PuntajeCriterio(id = 10, evaluacionId = 2, criterioId = 5, puntaje = 100, observacion = "Postulación impecable."),

        // Evaluacion 3 (Mariana Fuentes - Maria Fernandez)
        PuntajeCriterio(id = 11, evaluacionId = 3, criterioId = 1, puntaje = 98, observacion = "8 años de trayectoria comprobada en Cloud/DevOps."),
        PuntajeCriterio(id = 12, evaluacionId = 3, criterioId = 2, puntaje = 95, observacion = "Dominio avanzado de TI e infraestructura segura."),
        PuntajeCriterio(id = 13, evaluacionId = 3, criterioId = 3, puntaje = 90, observacion = "Habilidades de liderazgo bien consolidadas."),
        PuntajeCriterio(id = 14, evaluacionId = 3, criterioId = 4, puntaje = 95, observacion = "Alineación total con los objetivos estratégicos."),
        PuntajeCriterio(id = 15, evaluacionId = 3, criterioId = 5, puntaje = 95, observacion = "CV detallado con evidencias concretas."),

        // Evaluacion 4 (Mariana Fuentes - Andrea Torres)
        PuntajeCriterio(id = 16, evaluacionId = 4, criterioId = 1, puntaje = 95, observacion = "Trayectoria destacada en automatización e IA aplicada."),
        PuntajeCriterio(id = 17, evaluacionId = 4, criterioId = 2, puntaje = 92, observacion = "Sólidos conocimientos en herramientas TI."),
        PuntajeCriterio(id = 18, evaluacionId = 4, criterioId = 3, puntaje = 92, observacion = "Gran capacidad resolutiva."),
        PuntajeCriterio(id = 19, evaluacionId = 4, criterioId = 4, puntaje = 90, observacion = "Alto impacto potencial."),
        PuntajeCriterio(id = 20, evaluacionId = 4, criterioId = 5, puntaje = 90, observacion = "Presentación profesional estructurada.")
    )

    val HISTORIAL_INICIAL = listOf(
        RegistroHistorial(
            id = 1,
            candidataId = 1,
            usuarioNombre = "Sistema",
            usuarioRol = "Sistema",
            accion = RegistroHistorial.Acciones.REGISTRO_CANDIDATA,
            detalle = "Candidata registrada inicialmente: Camila Morales"
        ),
        RegistroHistorial(
            id = 2,
            candidataId = 2,
            usuarioNombre = "Sistema",
            usuarioRol = "Sistema",
            accion = RegistroHistorial.Acciones.REGISTRO_CANDIDATA,
            detalle = "Candidata registrada inicialmente: Valentina Silva"
        ),
        RegistroHistorial(
            id = 3,
            candidataId = 3,
            usuarioNombre = "Sistema",
            usuarioRol = "Sistema",
            accion = RegistroHistorial.Acciones.REGISTRO_CANDIDATA,
            detalle = "Candidata registrada inicialmente: Sofía Araya"
        ),
        RegistroHistorial(
            id = 4,
            candidataId = 4,
            usuarioNombre = "Sistema",
            usuarioRol = "Sistema",
            accion = RegistroHistorial.Acciones.REGISTRO_CANDIDATA,
            detalle = "Candidata registrada inicialmente: Mariana Fuentes"
        ),
        RegistroHistorial(
            id = 5,
            candidataId = 5,
            usuarioNombre = "Sistema",
            usuarioRol = "Sistema",
            accion = RegistroHistorial.Acciones.REGISTRO_CANDIDATA,
            detalle = "Candidata registrada inicialmente: Isadora Rojas"
        ),
        RegistroHistorial(
            id = 6,
            candidataId = 6,
            usuarioNombre = "Sistema",
            usuarioRol = "Sistema",
            accion = RegistroHistorial.Acciones.REGISTRO_CANDIDATA,
            detalle = "Candidata registrada inicialmente: Gabriela Castro"
        )
    )
}
