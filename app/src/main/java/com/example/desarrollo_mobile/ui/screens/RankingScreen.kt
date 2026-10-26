package com.example.desarrollo_mobile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.CalculadoraPuntaje
import com.example.desarrollo_mobile.ui.components.EstadoBadge
import com.example.desarrollo_mobile.ui.components.WimCard
import com.example.desarrollo_mobile.ui.components.WimTopBar
import com.example.desarrollo_mobile.ui.viewmodel.CandidatasViewModel
import java.util.Locale

@Composable
fun RankingScreen(
    viewModel: CandidatasViewModel,
    modifier: Modifier = Modifier,
    onCerrarSesionClick: (() -> Unit)? = null
) {
    val ranking by viewModel.rankingConsolidado.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            WimTopBar(
                title = "Ranking Consolidado",
                subtitle = "Panel de Dirección · Vista Global",
                onCerrarSesionClick = onCerrarSesionClick
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(ranking) { item ->
                    val categoria = CalculadoraPuntaje.obtenerCategoria(item.promedioPuntaje)

                    WimCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.candidataNombre,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            EstadoBadge(estado = item.estado)
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Área: ${item.area}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "Evaluaciones recibidas: ${item.cantidadEvaluaciones}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Puntaje: ${String.format(Locale.getDefault(), "%.1f", item.promedioPuntaje)} / 100",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Categoría: $categoria",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }
        }
    }
}
