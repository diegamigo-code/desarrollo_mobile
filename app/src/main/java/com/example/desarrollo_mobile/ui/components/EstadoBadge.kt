package com.example.desarrollo_mobile.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.EstadoCandidatura
import com.example.desarrollo_mobile.ui.theme.WimCyan
import com.example.desarrollo_mobile.ui.theme.WimDanger
import com.example.desarrollo_mobile.ui.theme.WimPrimary
import com.example.desarrollo_mobile.ui.theme.WimSuccess
import com.example.desarrollo_mobile.ui.theme.WimWarning

@Composable
fun EstadoBadge(estado: EstadoCandidatura, modifier: Modifier = Modifier) {
    val accent = when (estado) {
        EstadoCandidatura.POSTULADA -> WimCyan
        EstadoCandidatura.EN_EVALUACION -> WimWarning
        EstadoCandidatura.EVALUADA -> WimPrimary
        EstadoCandidatura.SELECCIONADA -> WimSuccess
        EstadoCandidatura.NO_SELECCIONADA -> WimDanger
    }
    Surface(
        color = accent.copy(alpha = 0.13f),
        contentColor = accent,
        shape = RoundedCornerShape(50),
        modifier = modifier
    ) {
        Text(estado.etiqueta.uppercase(), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
    }
}
