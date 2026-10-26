package com.example.desarrollo_mobile.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.CalculadoraPuntaje
import com.example.desarrollo_mobile.model.ResultadoConsolidado
import com.example.desarrollo_mobile.ui.components.EstadoBadge
import com.example.desarrollo_mobile.ui.components.WimCard
import com.example.desarrollo_mobile.ui.components.WimTopBar
import com.example.desarrollo_mobile.ui.theme.WimHeroGradient
import com.example.desarrollo_mobile.ui.theme.WimOutline
import com.example.desarrollo_mobile.ui.theme.WimPrimary
import com.example.desarrollo_mobile.ui.theme.WimPrimaryLight
import com.example.desarrollo_mobile.ui.theme.WimSecondary
import com.example.desarrollo_mobile.ui.theme.WimSecondaryLight
import com.example.desarrollo_mobile.ui.theme.WimSuccessLight
import com.example.desarrollo_mobile.ui.theme.WimText
import com.example.desarrollo_mobile.ui.viewmodel.CandidatasViewModel
import java.util.Locale

@Composable
fun RankingScreen(
    viewModel: CandidatasViewModel,
    modifier: Modifier = Modifier,
    onCerrarSesionClick: (() -> Unit)? = null
) {
    val ranking by viewModel.rankingConsolidado.collectAsState()
    val errContainer = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
    val errText = MaterialTheme.colorScheme.error

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
                subtitle = "Panel Dirección · Resultados Globales",
                onCerrarSesionClick = onCerrarSesionClick
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // HERO BANNER DE DIRECCIÓN
                item {
                    Surface(
                        shape = RoundedCornerShape(26.dp),
                        border = BorderStroke(1.dp, WimOutline),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .background(WimHeroGradient)
                                .padding(20.dp)
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Surface(
                                        color = Color.White.copy(alpha = 0.85f),
                                        shape = CircleShape
                                    ) {
                                        Text(
                                            text = "👑 PANEL DIRECTIVO",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = WimSecondary,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                    Text(text = "🏆", style = MaterialTheme.typography.titleLarge)
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Evaluación Consolidada WIM 100",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    color = WimText
                                )

                                Text(
                                    text = "Visualiza el puntaje ponderado de cada postulante según las evaluaciones recibidas.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = WimText.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }

                if (ranking.isEmpty()) {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("📊", style = MaterialTheme.typography.headlineLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Aún no hay evaluaciones registradas",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = WimText
                                )
                                Text(
                                    text = "A medida que las evaluadoras califiquen candidatas, aparecerán aquí.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                } else {
                    itemsIndexed(ranking) { index: Int, item: ResultadoConsolidado ->
                        val categoria = CalculadoraPuntaje.obtenerCategoria(item.promedioPuntaje)
                        val posicionIcon = when (index) {
                            0 -> "👑 1º"
                            1 -> "🥈 2º"
                            2 -> "🥉 3º"
                            else -> "✨ ${index + 1}º"
                        }

                        val (catBg, catTextColor) = when {
                            item.promedioPuntaje >= 85.0 -> Pair(WimSuccessLight, Color(0xFF2E7D32))
                            item.promedioPuntaje >= 70.0 -> Pair(WimSecondaryLight, WimSecondary)
                            item.promedioPuntaje >= 50.0 -> Pair(WimPrimaryLight, WimPrimary)
                            else -> Pair(errContainer, errText)
                        }

                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + slideInVertically(initialOffsetY = { 30 })
                        ) {
                            WimCard(
                                borderColor = if (index == 0) WimPrimary else WimOutline,
                                elevation = if (index == 0) 4.dp else 1.dp,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            color = if (index == 0) WimPrimaryLight else MaterialTheme.colorScheme.surfaceVariant,
                                            shape = CircleShape
                                        ) {
                                            Text(
                                                text = posicionIcon,
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = if (index == 0) WimPrimary else WimText,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(10.dp))

                                        Column {
                                            Text(
                                                text = item.candidataNombre,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = WimText
                                            )
                                            Text(
                                                text = "Área: ${item.area}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }

                                    EstadoBadge(estado = item.estado)
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // BARRA DE PUNTAJE PONDERADO
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Puntaje Ponderado",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "${String.format(Locale.getDefault(), "%.1f", item.promedioPuntaje)} / 100 pts",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = WimPrimary
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                LinearProgressIndicator(
                                    progress = { (item.promedioPuntaje / 100.0).toFloat().coerceIn(0f, 1f) },
                                    color = WimPrimary,
                                    trackColor = WimPrimaryLight,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Surface(
                                        color = catBg,
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "Categoría: $categoria",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = catTextColor,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }

                                    Text(
                                        text = "${item.cantidadEvaluaciones} evaluación(es)",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
