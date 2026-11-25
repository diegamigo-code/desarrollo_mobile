package com.example.desarrollo_mobile.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.CalculadoraPuntaje
import com.example.desarrollo_mobile.ui.components.*
import com.example.desarrollo_mobile.ui.theme.*
import com.example.desarrollo_mobile.ui.viewmodel.CandidatasViewModel
import java.util.Locale

@Composable
fun RankingScreen(viewModel: CandidatasViewModel, modifier: Modifier = Modifier,
    onCerrarSesionClick: (() -> Unit)? = null) {
    val ranking by viewModel.rankingConsolidado.collectAsState()
    Column(modifier.fillMaxSize().statusBarsPadding()) {
        WimTopBar("Ranking", "Dirección", onCerrarSesionClick)
        LazyColumn(contentPadding = PaddingValues(horizontal = WimDesign.page, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (ranking.isEmpty()) item { Text("Aún no hay candidaturas para comparar.", color = WimMuted) }
            itemsIndexed(ranking, key = { _, item -> item.candidataId }) { index, item ->
                WimCard(containerColor = if (index == 0 && item.cantidadEvaluaciones > 0) WimPrimaryLight else WimSurface) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text((index + 1).toString().padStart(2, '0'), color = WimPrimary, style = MaterialTheme.typography.titleLarge)
                        Column(Modifier.weight(1f)) {
                            Text(item.candidataNombre, style = MaterialTheme.typography.headlineSmall)
                            Text(item.area, style = MaterialTheme.typography.bodySmall, color = WimMuted)
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    EstadoBadge(item.estado)
                    HorizontalDivider(Modifier.padding(vertical = 12.dp), color = WimOutline)
                    if (item.cantidadEvaluaciones == 0) Text("Pendiente de evaluación", color = WimMuted)
                    else {
                        Text(String.format(Locale.getDefault(), "%.1f / 100", item.promedioPuntaje),
                            style = MaterialTheme.typography.titleLarge, color = WimPrimaryStrong)
                        val progress by animateFloatAsState((item.promedioPuntaje / 100).toFloat().coerceIn(0f, 1f), label = "Puntaje")
                        LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            color = WimPrimary, trackColor = WimOutline)
                        val cantidad = item.cantidadEvaluaciones
                        val evaluaciones = if (cantidad == 1) "1 evaluación" else "$cantidad evaluaciones"
                        Text("${CalculadoraPuntaje.obtenerCategoria(item.promedioPuntaje)} · $evaluaciones",
                            style = MaterialTheme.typography.bodyMedium, color = WimMuted)
                    }
                }
            }
        }
    }
}
