package com.example.desarrollo_mobile.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    val criterios by viewModel.criterios.collectAsState()

    LaunchedEffect(state.guardadoExitoso) {
        if (state.guardadoExitoso) {
            onVolverClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Evaluación Matriz Técnica") },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            criterios.forEach { criterio ->
                val notaActual = state.puntajes[criterio.id] ?: 0
                val obsActual = state.observacionesCriterio[criterio.id] ?: ""
                val errorObs = state.erroresCriterio[criterio.id]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${criterio.nombre} (${criterio.pesoPorcentaje}%)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = criterio.descripcion,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Calificación (1 a 5):", style = MaterialTheme.typography.labelMedium)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            (1..5).forEach { nota ->
                                RadioButton(
                                    selected = notaActual == nota,
                                    onClick = { viewModel.onPuntajeCambiado(criterio.id, nota) }
                                )
                                Text(text = "$nota", modifier = Modifier.padding(end = 8.dp))
                            }
                        }

                        if (notaActual in 1..2) {
                            OutlinedTextField(
                                value = obsActual,
                                onValueChange = { viewModel.onObservacionCriterioCambiada(criterio.id, it) },
                                label = { Text("Justificación obligatoria (mín. 10 caract.)") },
                                isError = errorObs != null,
                                supportingText = { errorObs?.let { Text(it) } },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.observacionGeneral,
                onValueChange = { viewModel.onObservacionGeneralCambiada(it) },
                label = { Text("Observación General (20 a 400 caracteres)") },
                isError = state.errorObservacionGeneral != null,
                supportingText = { state.errorObservacionGeneral?.let { Text(it) } },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            state.errorGeneral?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.registrarEvaluacion(
                        candidataId = candidataId,
                        evaluadoraClave = evaluadoraClave,
                        evaluadoraNombre = evaluadoraNombre
                    )
                },
                enabled = !state.estaGuardando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.estaGuardando) "Guardando..." else "Finalizar Evaluación")
            }
        }
    }
}