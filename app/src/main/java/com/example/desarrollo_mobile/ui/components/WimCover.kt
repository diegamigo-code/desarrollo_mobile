package com.example.desarrollo_mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.ui.theme.WimDesign

/** Roseta trazada con curvas Bézier. El dibujo se recalcula solo al cambiar el tamaño. */
@Composable
fun WimCover(label: String, title: String, detail: String? = null) {
    Box(Modifier.fillMaxWidth().clip(WimDesign.cardShape)
        .background(Brush.linearGradient(listOf(Color(0xFF381C30), Color(0xFF66364E))))) {
        Spacer(Modifier.matchParentSize().drawWithCache {
            val origin = androidx.compose.ui.geometry.Offset(size.width * .91f, size.height * .55f)
            val radius = minOf(size.width * .34f, size.height * .86f)
            val lines = (0..12).map { i ->
                val width = radius * (.12f + i * .025f)
                Path().apply {
                    moveTo(origin.x, origin.y)
                    cubicTo(origin.x - width, origin.y - radius * .44f,
                        origin.x - width, origin.y - radius, origin.x, origin.y - radius)
                    cubicTo(origin.x + width, origin.y - radius,
                        origin.x + width, origin.y - radius * .35f, origin.x, origin.y)
                }
            }
            val ink = Color(0xFFFFC6D8).copy(alpha = .42f)
            val stroke = Stroke(.65.dp.toPx())
            onDrawBehind {
                repeat(8) { i -> rotate(i * 45f + 12f, origin) {
                    lines.forEach { drawPath(it, ink, style = stroke) }
                } }
            }
        })
        Column(Modifier.align(Alignment.CenterStart).fillMaxWidth(.76f).padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(label, style = MaterialTheme.typography.labelLarge, color = Color(0xFFFFCDDC))
            Text(title, style = MaterialTheme.typography.displaySmall, color = Color.White)
            detail?.let { Text(it, style = MaterialTheme.typography.bodyMedium, color = Color(0xFFFFE9F0)) }
        }
    }
}
