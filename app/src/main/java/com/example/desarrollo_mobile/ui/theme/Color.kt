package com.example.desarrollo_mobile.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Paleta WIM 100 — Edición Pastel & Femenina Elegante
val WimBackground = Color(0xFFFFF9FA)         // Fondo crema pastel rosado suave
val WimSurface = Color(0xFFFFFFFF)            // Blanco puro para tarjetas
val WimSurfaceElevated = Color(0xFFF9EFF5)    // Elevación suave pastel lilácea
val WimSurfaceVariant = Color(0xFFF4E8F0)     // Contenedores secundarios suave

// Colores Primarios y Secundarios Pastel
val WimPrimary = Color(0xFFE8829C)            // Rosa pastel elegante principal
val WimPrimaryStrong = Color(0xFFD66080)      // Rosa acento intenso
val WimPrimaryLight = Color(0xFFFDE8ED)       // Rosa contenedor muy suave

val WimSecondary = Color(0xFFB39DDB)          // Lavanda / Lilá pastel elegante
val WimSecondaryStrong = Color(0xFF8E24AA)     // Lavanda acento
val WimSecondaryLight = Color(0xFFF3E5F5)      // Lilá contenedor suave

val WimTertiary = Color(0xFFFFB7B2)           // Durazno pastel suave
val WimTertiaryLight = Color(0xFFFFF0EC)      // Durazno contenedor

// Estados & Badges Pastel
val WimSuccess = Color(0xFF81C784)            // Menta pastel suave (Seleccionada)
val WimSuccessLight = Color(0xEBF0F9F1)       // Fondo menta
val WimWarning = Color(0xFFFFB74D)            // Dorado pastel cálido (En Evaluación)
val WimWarningLight = Color(0xFFFFF6E5)       // Fondo dorado
val WimCyan = Color(0xFF64B5F6)               // Azul cielo pastel (Postulada / Plataforma)
val WimCyanLight = Color(0xFFEBF5FE)          // Fondo azul cielo
val WimDanger = Color(0xFFE57373)             // Coral/Rosa empolvado pastel (No seleccionada)
val WimDangerLight = Color(0xFFFDEAEA)         // Fondo coral pastel

// Textos y Bordes
val WimText = Color(0xFF2E2028)               // Marrón chocolate / ciruela oscuro (legible)
val WimMuted = Color(0xFF887380)              // Malva empolvado suave para subtítulos
val WimOutline = Color(0xFFF0D5DF)            // Borde delicado pastel
val WimOutlineFocused = Color(0xFFE8829C)     // Borde enfocado rosa pastel

// Gradientes Pastel Elegantes
val WimHeroGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFFFFDFE5), // Rosa algodón
        Color(0xFFE8D7F5), // Lilá suave
        Color(0xFFFFF0E6)  // Durazno pastel
    )
)

val WimCardGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFFAF2F7)
    )
)

val WimAccentGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFFF092A9),
        Color(0xFFB39DDB)
    )
)
