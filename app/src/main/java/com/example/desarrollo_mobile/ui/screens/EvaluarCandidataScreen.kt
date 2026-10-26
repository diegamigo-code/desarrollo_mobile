package com.example.desarrollo_mobile.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.Criterio
import com.example.desarrollo_mobile.ui.components.WimButton
import com.example.desarrollo_mobile.ui.components.WimCard
import com.example.desarrollo_mobile.ui.components.WimTextField
import com.example.desarrollo_mobile.ui.theme.WimOutline
import com.example.desarrollo_mobile.ui.theme.WimPrimary
import com.example.desarrollo_mobile.ui.theme.WimPrimaryLight
import com.example.desarrollo_mobile.ui.theme.WimSecondary
import com.example.desarrollo_mobile.ui.theme.WimText
import com.example.desarrollo_mobile.ui.viewmodel.EvaluacionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvaluarCandidataScreen(
    candidataId: Long,
    viewModel: EvaluacionViewModel,
    evaluadoraClave: String,
    evaluadoraNombre: String,
    onVolverClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.formState.collectAsState()
    val criteriosList: List<Criterio> by viewModel.criterios.collectAsState()

    LaunchedEffect(state.guardadoExitoso) {
        if (state.guardadoExitoso) {
            onVolverClick()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🌸", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Matriz de Evaluación Técnica",
                            fontWeight = FontWeight.Bold,
                            color = WimText
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onVolverClick,
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .background(WimPrimaryLight)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = WimPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // HEADER EVALUADORA
            WimCard(
                borderColor = WimOutline,
                containerColor = Color.White
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = WimPrimaryLight,
                        shape = CircleShape,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = WimPrimary,
                            modifier = Modifier
                                .padding(12.dp)
                                .size(24.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Evaluación Ponderada de Talento",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = WimText
                        )
                        Text(
                            text = "Evaluadora: $evaluadoraNombre",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LISTA DE CRITERIOS
            criteriosList.forEach { criterio: Criterio ->
                val notaActual: Int = state.puntajes[criterio.id] ?: 0
                val obsActual: String = state.observacionesCriterio[criterio.id] ?: ""
                val errorObs: String? = state.erroresCriterio[criterio.id]

                WimCard(
                    borderColor = if (notaActual > 0) WimPrimary.copy(alpha = 0.5f) else WimOutline,
                    modifier = Modifier.padding(vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = criterio.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = WimText,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            color = WimPrimaryLight,
                            shape = CircleShape
                        ) {
                            Text(
                                text = "${criterio.pesoPorcentaje}%",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = WimPrimary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = criterio.descripcion,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Calificación (1 a 5 pts):",
                        style = MaterialTheme.typography.labelSmall,
                        color = WimSecondary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // BOTONES SELECTORES TIPO PILL PASTEL (1 A 5)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        (1..5).forEach { nota ->
                            val selected = notaActual == nota
                            val bgAnim by animateColorAsState(
                                targetValue = if (selected) WimPrimary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                label = "NotaBgAnim"
                            )
                            val textAnim by animateColorAsState(
                                targetValue = if (selected) Color.White else WimText,
                                label = "NotaTextAnim"
                            )

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(CircleShape)
                                    .background(bgAnim)
                                    .clickable { viewModel.onPuntajeCambiado(criterio.id, nota) }
                                    .padding(vertical = 10.dp)
                            ) {
                                Text(
                                    text = "$nota ⭐",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                    color = textAnim
                                )
                            }
                        }
                    }

                    // JUSTIFICACIÓN OBLIGATORIA SI ES 1 O 2
                    AnimatedVisibility(visible = notaActual in 1..2) {
                        Column(modifier = Modifier.padding(top = 12.dp)) {
                            WimTextField(
                                value = obsActual,
                                onValueChange = { viewModel.onObservacionCriterioCambiada(criterio.id, it) },
                                label = "Justificación obligatoria (mínimo 10 caracteres)",
                                isError = errorObs != null,
                                supportingText = errorObs?.let { msg -> { Text(msg, color = MaterialTheme.colorScheme.error) } },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // OBSERVACIÓN GENERAL
            WimCard(borderColor = WimOutline) {
                Text(
                    text = "OBSERVACIÓN GENERAL DEL PROCESO",
                    style = MaterialTheme.typography.labelSmall,
                    color = WimSecondary,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                WimTextField(
                    value = state.observacionGeneral,
                    onValueChange = { viewModel.onObservacionGeneralCambiada(it) },
                    label = "Observaciones de cierre (20 a 400 caracteres)",
                    singleLine = false,
                    isError = state.errorObservacionGeneral != null,
                    supportingText = state.errorObservacionGeneral?.let { msg -> { Text(msg, color = MaterialTheme.colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            state.errorGeneral?.let { err ->
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = err,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            WimButton(
                text = if (state.estaGuardando) "Guardando..." else "Finalizar y Registrar Evaluación ✨",
                onClick = {
                    viewModel.registrarEvaluacion(
                        candidataId = candidataId,
                        evaluadoraClave = evaluadoraClave,
                        evaluadoraNombre = evaluadoraNombre
                    )
                },
                enabled = !state.estaGuardando,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
