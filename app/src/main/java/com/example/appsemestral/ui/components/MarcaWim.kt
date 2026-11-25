package com.example.appsemestral.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/** Monograma vectorial W con órbita: liderazgo y conexión. Entrada finita, sin bucles. */
@Composable
fun MarcaWim(modifier: Modifier = Modifier) {
    val entrada = remember { Animatable(0f) }
    LaunchedEffect(Unit) { entrada.animateTo(1f, tween(700)) }
    val tinta = MaterialTheme.colorScheme.primary
    val fondo = MaterialTheme.colorScheme.primaryContainer
    val acento = MaterialTheme.colorScheme.secondary
    Canvas(modifier.size(104.dp).semantics { contentDescription = "WIM 100" }
        .graphicsLayer { alpha = entrada.value; scaleX = .9f + .1f * entrada.value; scaleY = scaleX }) {
        val u = size.minDimension / 100f
        drawCircle(fondo)
        drawArc(tinta.copy(alpha = .4f), -65f, 290f, false,
            topLeft = Offset(7*u, 7*u), size = Size(86*u, 86*u), style = Stroke(1.2f*u))
        val monograma = Path().apply {
            moveTo(25*u, 36*u); lineTo(36*u, 65*u); lineTo(50*u, 43*u)
            lineTo(64*u, 65*u); lineTo(75*u, 36*u)
        }
        drawPath(monograma, tinta, style = Stroke(4*u, cap = StrokeCap.Round))
        drawCircle(acento, 4*u, Offset(80*u, 20*u))
    }
}
