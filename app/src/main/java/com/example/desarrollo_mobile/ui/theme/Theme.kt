package com.example.desarrollo_mobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WimPastelColorScheme = lightColorScheme(
    primary = WimPrimary,
    onPrimary = Color.White,
    primaryContainer = WimPrimaryLight,
    onPrimaryContainer = Color(0xFF70283C),

    secondary = WimSecondary,
    onSecondary = Color.White,
    secondaryContainer = WimSecondaryLight,
    onSecondaryContainer = Color(0xFF4A148C),

    tertiary = WimTertiary,
    onTertiary = Color(0xFF5D2D23),
    tertiaryContainer = WimTertiaryLight,
    onTertiaryContainer = Color(0xFF6E3930),

    background = WimBackground,
    onBackground = WimText,

    surface = WimSurface,
    onSurface = WimText,
    surfaceVariant = WimSurfaceVariant,
    onSurfaceVariant = WimMuted,

    outline = WimOutline,
    outlineVariant = Color(0xFFF9E4EB),

    error = WimDanger,
    onError = Color.White,
    errorContainer = WimDangerLight,
    onErrorContainer = Color(0xFF7A2020)
)

@Composable
fun Desarrollo_mobileTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = WimPastelColorScheme,
        typography = Typography,
        content = content
    )
}
