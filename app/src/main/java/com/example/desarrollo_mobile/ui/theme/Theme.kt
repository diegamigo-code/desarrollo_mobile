package com.example.desarrollo_mobile.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// 1. Declaración previa de las variables de contenedor de color
private val ColorContainerPrimary = Color(0xFF382B61)
private val ColorContainerSecondary = Color(0xFF173D49)

// 2. Esquema de colores utilizando las variables ya inicializadas
private val WimColorScheme = darkColorScheme(
    primary = WimPrimary,
    onPrimary = WimBackground,
    primaryContainer = ColorContainerPrimary,
    onPrimaryContainer = WimText,
    secondary = WimCyan,
    onSecondary = WimBackground,
    secondaryContainer = ColorContainerSecondary,
    onSecondaryContainer = WimText,
    tertiary = WimSuccess,
    background = WimBackground,
    onBackground = WimText,
    surface = WimSurface,
    onSurface = WimText,
    surfaceVariant = WimSurfaceElevated,
    onSurfaceVariant = WimMuted,
    outline = WimOutline,
    error = WimDanger,
    onError = WimBackground,
    errorContainer = Color(0xFF482536),
    onErrorContainer = WimText
)

@Composable
fun Desarrollo_mobileTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = WimColorScheme,
        typography = Typography,
        content = content
    )
}