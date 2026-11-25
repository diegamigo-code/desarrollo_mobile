package com.example.appsemestral.ui.theme

import androidx.compose.ui.unit.dp
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val EsquemaClaro = lightColorScheme(
    primary = Violeta40,
    onPrimary = Color.White,
    primaryContainer = Violeta90,
    onPrimaryContainer = Violeta10,
    secondary = Turquesa40,
    onSecondary = Color.White,
    secondaryContainer = Turquesa90,
    onSecondaryContainer = Turquesa10,
    tertiary = Magenta40,
    onTertiary = Color.White,
    tertiaryContainer = Magenta90,
    onTertiaryContainer = Magenta10,
    background = Fondo,
    surface = Color(0xFFFFFCF7),
    onBackground = Color(0xFF302B25),
    onSurface = Color(0xFF302B25),
    onSurfaceVariant = Color(0xFF655E55),
    surfaceVariant = Color(0xFFEDE6DC),
    surfaceContainer = Color(0xFFF3EDE4),
    surfaceContainerHigh = Color(0xFFEDE5DA),
    outline = Color(0xFF81766A),
    outlineVariant = Color(0xFFD9CFC1)
)

private val EsquemaOscuro = darkColorScheme(
    primary = Violeta80,
    onPrimary = Violeta10,
    primaryContainer = Violeta30,
    onPrimaryContainer = Violeta90,
    secondary = Turquesa80,
    onSecondary = Turquesa10,
    secondaryContainer = Turquesa30,
    onSecondaryContainer = Turquesa90,
    tertiary = Magenta80,
    onTertiary = Magenta10,
    tertiaryContainer = Magenta30,
    onTertiaryContainer = Magenta90,
    background = FondoOscuro,
    surface = FondoOscuro
)

/**
 * Tema Material Design 3 con modo claro y oscuro.
 * dynamicColor = false para mantener la identidad visual WIM 100 (con true usa los colores del fondo de pantalla, Android 12+).
 */
@Composable
fun AppSemestralTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> EsquemaOscuro
        else -> EsquemaClaro
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Tipografia,
        shapes = androidx.compose.material3.Shapes(
            small = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            medium = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            large = androidx.compose.foundation.shape.RoundedCornerShape(28.dp)
        ),
        content = content
    )
}
