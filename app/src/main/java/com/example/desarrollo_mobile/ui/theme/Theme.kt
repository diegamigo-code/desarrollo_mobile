package com.example.desarrollo_mobile.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

enum class Appearance { Automático, Claro, Oscuro }
val LocalAppearance = compositionLocalOf { Appearance.Automático }
val LocalSetAppearance = compositionLocalOf<(Appearance) -> Unit> { {} }

@Composable
fun Desarrollo_mobileTheme(content: @Composable () -> Unit) {
    val prefs = LocalContext.current.getSharedPreferences("appearance", 0)
    var mode by remember { mutableStateOf(Appearance.entries.firstOrNull { it.name == prefs.getString("theme", null) } ?: Appearance.Automático) }
    val dark = mode == Appearance.Oscuro || (mode == Appearance.Automático && isSystemInDarkTheme())
    CompositionLocalProvider(LocalDark provides dark, LocalAppearance provides mode, LocalSetAppearance provides { next ->
        mode = next
        prefs.edit().putString("theme", next.name).apply()
    }) {
        val base = if (dark) darkColorScheme() else lightColorScheme()
        MaterialTheme(colorScheme = base.copy(primary = WimPrimary, onPrimary = WimBackground,
            primaryContainer = WimPrimaryLight, onPrimaryContainer = WimText, secondary = WimSecondary,
            secondaryContainer = WimSecondaryLight, onSecondaryContainer = WimText, background = WimBackground,
            surface = WimSurface, onSurface = WimText, onBackground = WimText, surfaceVariant = WimSurfaceVariant,
            onSurfaceVariant = WimMuted, outline = WimOutline, error = WimDanger, errorContainer = WimDangerLight),
            typography = Typography, content = content)
    }
}
