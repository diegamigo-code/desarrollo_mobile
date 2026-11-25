package com.example.desarrollo_mobile.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.*

@Composable
fun OrbitalBanner(title: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().clip(RoundedCornerShape(28.dp)).background(
        Brush.linearGradient(listOf(Color(0xFF361629), Color(0xFF64273F), Color(0xFF371B44))))) {
        Canvas(Modifier.matchParentSize()) {
            val radius = min(size.width * .24f, size.height * .56f)
            val origin = Offset(size.width * .88f, size.height * .50f)
            for (ring in 0 until 54) {
                val angle = ring * 2 * PI / 54
                val path = Path()
                for (step in 0..72) {
                    val t = step * 2 * PI / 72
                    val r = .72 + .32 * cos(t)
                    val x = r * cos(angle)
                    val y = r * sin(angle) * .54 + .32 * sin(t)
                    val px = origin.x + radius * (x * .86 + y * .50).toFloat()
                    val py = origin.y + radius * (y * .86 - x * .50).toFloat()
                    if (step == 0) path.moveTo(px, py) else path.lineTo(px, py)
                }
                drawPath(path, Color(0xFFFFB6D9).copy(alpha = (.46 + .32 * cos(angle)).toFloat()), style = Stroke(1.dp.toPx()))
            }
        }
        Text(title, Modifier.align(Alignment.CenterStart).fillMaxWidth(.68f).padding(22.dp),
            color = Color(0xFFFFEFF6), style = MaterialTheme.typography.headlineLarge)
    }
}
