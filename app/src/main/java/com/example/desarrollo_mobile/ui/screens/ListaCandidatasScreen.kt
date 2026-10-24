package com.example.desarrollo_mobile.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.Rol
import com.example.desarrollo_mobile.ui.components.EstadoBadge
import com.example.desarrollo_mobile.ui.viewmodel.CandidatasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaCandidatasScreen(
    viewModel: CandidatasViewModel,
    rolActual: Rol,
    onNuevaCandidataClick: () -> Unit,
    onCandidataClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val candidatas by viewModel.candidatasFiltradas.collectAsState()
    val query by viewModel.busquedaQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Candidatas WIM 100") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNuevaCandidataClick) {
                Icon(Icons.Default.Add, contentDescription = "Nueva Candidata")
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onBusquedaCambiada(it) },
                label = { Text("Buscar candidata o área...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(candidatas) { candidata ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCandidataClick(candidata.id) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = candidata.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                EstadoBadge(estado = candidata.estado)
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Área: ${candidata.area}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Experiencia: ${candidata.anosExperiencia} años", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
