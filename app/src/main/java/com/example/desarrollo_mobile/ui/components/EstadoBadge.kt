package com.example.desarrollo_mobile.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.EstadoCandidatura

@Composable
fun EstadoBadge(
    estado: EstadoCandidatura,
    modifier: Modifier = Modifier
) {
    val containerColor = when (estado) {
        EstadoCandidatura.POSTULADA -> MaterialTheme.colorScheme.secondaryContainer
        EstadoCandidatura.EN_EVALUACION -> MaterialTheme.colorScheme.tertiaryContainer
        EstadoCandidatura.EVALUADA -> MaterialTheme.colorScheme.surfaceVariant
        EstadoCandidatura.SELECCIONADA -> MaterialTheme.colorScheme.primaryContainer
        EstadoCandidatura.NO_SELECCIONADA -> MaterialTheme.colorScheme.errorContainer
    }

    val contentColor = when (estado) {
        EstadoCandidatura.POSTULADA -> MaterialTheme.colorScheme.onSecondaryContainer
        EstadoCandidatura.EN_EVALUACION -> MaterialTheme.colorScheme.onTertiaryContainer
        EstadoCandidatura.EVALUADA -> MaterialTheme.colorScheme.onSurfaceVariant
        EstadoCandidatura.SELECCIONADA -> MaterialTheme.colorScheme.onPrimaryContainer
        EstadoCandidatura.NO_SELECCIONADA -> MaterialTheme.colorScheme.onErrorContainer
    }

    Surface(
        color = containerColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Text(
            text = estado.etiqueta,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
