package com.example.desarrollo_mobile.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.EstadoCandidatura
import com.example.desarrollo_mobile.ui.theme.WimCyan
import com.example.desarrollo_mobile.ui.theme.WimCyanLight
import com.example.desarrollo_mobile.ui.theme.WimDanger
import com.example.desarrollo_mobile.ui.theme.WimDangerLight
import com.example.desarrollo_mobile.ui.theme.WimPrimary
import com.example.desarrollo_mobile.ui.theme.WimPrimaryLight
import com.example.desarrollo_mobile.ui.theme.WimSuccess
import com.example.desarrollo_mobile.ui.theme.WimSuccessLight
import com.example.desarrollo_mobile.ui.theme.WimWarning
import com.example.desarrollo_mobile.ui.theme.WimWarningLight

@Composable
fun EstadoBadge(
    estado: EstadoCandidatura,
    modifier: Modifier = Modifier,
    animate: Boolean = true
) {
    val scaleState by animateFloatAsState(
        targetValue = if (animate) 1.0f else 0.95f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
        label = "BadgeScale"
    )

    val (bg, contentColor, icon) = when (estado) {
        EstadoCandidatura.POSTULADA -> Triple(WimCyanLight, WimCyan, "✨")
        EstadoCandidatura.EN_EVALUACION -> Triple(WimWarningLight, Color(0xFFD97706), "⏳")
        EstadoCandidatura.EVALUADA -> Triple(WimPrimaryLight, WimPrimary, "🌸")
        EstadoCandidatura.SELECCIONADA -> Triple(WimSuccessLight, Color(0xFF2E7D32), "🏆")
        EstadoCandidatura.NO_SELECCIONADA -> Triple(WimDangerLight, WimDanger, "💗")
    }

    Surface(
        color = bg,
        contentColor = contentColor,
        shape = CircleShape,
        border = BorderStroke(1.dp, contentColor.copy(alpha = 0.35f)),
        modifier = modifier.scale(scaleState)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
            Text(text = icon, style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = estado.etiqueta.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
