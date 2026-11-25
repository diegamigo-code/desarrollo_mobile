package com.example.desarrollo_mobile.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*

internal val LocalDark = staticCompositionLocalOf { false }

val WimBackground: Color @Composable get() = if (LocalDark.current) Color(0xFF291D24) else Color(0xFFFBF6F4)
val WimSurface: Color @Composable get() = if (LocalDark.current) Color(0xFF30202C) else Color(0xFFFFFFFF)
val WimSurfaceElevated: Color @Composable get() = if (LocalDark.current) Color(0xFF432D3C) else Color(0xFFFFE0EB)
val WimSurfaceVariant: Color @Composable get() = if (LocalDark.current) Color(0xFF453240) else Color(0xFFF7E7ED)
val WimPrimary: Color @Composable get() = if (LocalDark.current) Color(0xFFFFAACA) else Color(0xFF81294F)
val WimPrimaryStrong: Color @Composable get() = if (LocalDark.current) Color(0xFFFFD5E6) else Color(0xFF742045)
val WimPrimaryLight: Color @Composable get() = if (LocalDark.current) Color(0xFF58283F) else Color(0xFFF5D8E2)
val WimSecondary: Color @Composable get() = if (LocalDark.current) Color(0xFFE8B3EF) else Color(0xFF754477)
val WimSecondaryStrong: Color @Composable get() = if (LocalDark.current) Color(0xFFBBEBC9) else Color(0xFF185337)
val WimSecondaryLight: Color @Composable get() = if (LocalDark.current) Color(0xFF3B2940) else Color(0xFFF5DEF6)
val WimTertiary: Color @Composable get() = if (LocalDark.current) Color(0xFFA8D2EC) else Color(0xFF365A70)
val WimTertiaryLight: Color @Composable get() = if (LocalDark.current) Color(0xFF273B48) else Color(0xFFDCEAF2)
val WimSuccess: Color @Composable get() = if (LocalDark.current) Color(0xFFE8B3EF) else Color(0xFF26613C)
val WimSuccessLight: Color @Composable get() = if (LocalDark.current) Color(0xFF3B2940) else Color(0xFFF5DEF6)
val WimWarning: Color @Composable get() = if (LocalDark.current) Color(0xFFF6C881) else Color(0xFF865100)
val WimWarningLight: Color @Composable get() = if (LocalDark.current) Color(0xFF45351E) else Color(0xFFFFF0D8)
val WimCyan: Color @Composable get() = if (LocalDark.current) Color(0xFFA4D9F9) else Color(0xFF265E80)
val WimCyanLight: Color @Composable get() = if (LocalDark.current) Color(0xFF203945) else Color(0xFFDEF1FC)
val WimDanger: Color @Composable get() = if (LocalDark.current) Color(0xFFFFAEB8) else Color(0xFFA13448)
val WimDangerLight: Color @Composable get() = if (LocalDark.current) Color(0xFF512735) else Color(0xFFFFE6EA)
val WimText: Color @Composable get() = if (LocalDark.current) Color(0xFFFBF6F4) else Color(0xFF291D24)
val WimMuted: Color @Composable get() = if (LocalDark.current) Color(0xFFEACBD7) else Color(0xFF705462)
val WimOutline: Color @Composable get() = if (LocalDark.current) Color(0xFF725466) else Color(0xFFDBCBD0)
val WimOutlineFocused: Color @Composable get() = if (LocalDark.current) Color(0xFFFFAACA) else Color(0xFF81294F)
val WimHeroGradient: Brush @Composable get() = Brush.linearGradient(listOf(WimPrimaryLight, WimSurfaceElevated, WimSecondaryLight))
val WimCardGradient: Brush @Composable get() = Brush.linearGradient(listOf(WimSurface, WimSurfaceElevated))
val WimAccentGradient: Brush @Composable get() = Brush.linearGradient(listOf(WimPrimary, WimSecondary))
