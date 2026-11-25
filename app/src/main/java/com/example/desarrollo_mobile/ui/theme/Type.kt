package com.example.desarrollo_mobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val brand = FontFamily(androidx.compose.ui.text.font.Font(com.example.desarrollo_mobile.R.font.adwaita_sans))
private val defaults = Typography()
val Typography = Typography(
    titleLarge = defaults.titleLarge.copy(fontFamily = brand),
    titleMedium = defaults.titleMedium.copy(fontFamily = brand),
    titleSmall = defaults.titleSmall.copy(fontFamily = brand),
    headlineSmall = defaults.headlineSmall.copy(fontFamily = FontFamily.Serif, letterSpacing = (-0.5).sp),
    headlineMedium = defaults.headlineMedium.copy(fontFamily = brand),
    bodyMedium = defaults.bodyMedium.copy(fontFamily = brand),
    bodySmall = defaults.bodySmall.copy(fontFamily = brand),
    labelLarge = defaults.labelLarge.copy(fontFamily = brand),
    labelMedium = defaults.labelMedium.copy(fontFamily = brand),
    labelSmall = defaults.labelSmall.copy(fontFamily = brand),
    displaySmall = TextStyle(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Normal,
        fontSize = 38.sp, lineHeight = 44.sp, letterSpacing = (-1).sp),
    headlineLarge = TextStyle(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Normal,
        fontSize = 30.sp, lineHeight = 38.sp),
    bodyLarge = TextStyle(fontFamily = brand, fontSize = 16.sp, lineHeight = 24.sp)
)
