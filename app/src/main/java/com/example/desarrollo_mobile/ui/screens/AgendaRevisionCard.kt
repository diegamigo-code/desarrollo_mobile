package com.example.desarrollo_mobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.AgendaRevision
import com.example.desarrollo_mobile.model.Rol
import com.example.desarrollo_mobile.ui.components.*
import com.example.desarrollo_mobile.ui.theme.*

@Composable
fun DashboardContent(agenda: AgendaRevision, rol: Rol, abrir: (Long) -> Unit,
    registrar: () -> Unit, ranking: () -> Unit) {
    val evaluadora = rol == Rol.EVALUADORA
    val proporcion = if (agenda.total == 0) 0f else agenda.conEvaluacion.toFloat() / agenda.total
    if (evaluadora) {
        WimCover("Primera revisión", agenda.pendientes.toString(),
            if (agenda.pendientes == 1) "candidatura pendiente" else "candidaturas pendientes")
    } else {
        WimCover("Cobertura de evaluación", "${kotlin.math.round(proporcion * 100).toInt()} %",
            "${agenda.conEvaluacion} de ${agenda.total} candidaturas")
    }
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Metric(if (evaluadora) agenda.conEvaluacion else agenda.pendientes,
            if (evaluadora) "Con evaluación" else "Por revisar", Modifier.weight(1f))
        Metric(agenda.seleccionadas, "Seleccionadas", Modifier.weight(1f))
    }
    if (evaluadora) {
        WimCard {
            val siguiente = agenda.siguiente
            Text(if (siguiente == null) "Primera revisión al día" else "Siguiente candidatura",
                style = MaterialTheme.typography.labelLarge, color = WimPrimary)
            siguiente?.let {
                Spacer(Modifier.height(8.dp))
                Text(it.candidataNombre, style = MaterialTheme.typography.headlineSmall)
                Text(it.area, color = WimMuted)
                Spacer(Modifier.height(16.dp))
                WimButton("Revisar candidatura", { abrir(it.candidataId) }, Modifier.fillMaxWidth())
            }
            if (agenda.total == 0) TextButton(onClick = registrar) { Text("Registrar primera candidatura") }
        }
        Text("Cobertura de evaluación · ${agenda.conEvaluacion} de ${agenda.total}", style = MaterialTheme.typography.titleSmall)
        LinearProgressIndicator(progress = { proporcion }, modifier = Modifier.fillMaxWidth(), color = WimPrimary, trackColor = WimPrimaryLight)
    } else WimButton("Consultar ranking", ranking, Modifier.fillMaxWidth())
}

@Composable
private fun Metric(value: Int, label: String, modifier: Modifier) {
    WimCard(modifier = modifier) {
        Text(value.toString(), style = MaterialTheme.typography.headlineLarge, color = WimPrimaryStrong)
        Text(label, style = MaterialTheme.typography.bodyMedium, color = WimMuted)
    }
}
