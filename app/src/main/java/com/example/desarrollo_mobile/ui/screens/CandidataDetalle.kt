package com.example.desarrollo_mobile.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.*
import com.example.desarrollo_mobile.ui.components.*
import com.example.desarrollo_mobile.ui.theme.*

@Composable
fun CandidataDetalle(cargada: Boolean, candidata: Candidata?, historial: List<RegistroHistorial>,
    titulo: Modifier, puedeEditar: Boolean, onBack: () -> Unit, onEvaluar: () -> Unit, onEditar: () -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(Modifier.systemBarsPadding().verticalScroll(rememberScrollState()).padding(WimDesign.page),
        verticalArrangement = Arrangement.spacedBy(WimDesign.gap)) {
        TextButton(onClick = onBack) { Text("Volver") }
        if (!cargada) {
            Column(Modifier.semantics { contentDescription = "Cargando candidatura" },
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(0.7f, 1f, 0.85f).forEach { width ->
                    Spacer(Modifier.fillMaxWidth(width).height(24.dp).background(WimSurfaceVariant, WimDesign.cardShape))
                }
                LinearProgressIndicator()
            }
        } else if (candidata == null) {
            Text("Esta candidatura ya no está disponible.")
        } else {
            Box(titulo) {
                WimCover("Ficha de candidatura", candidata.nombre, candidata.area)
            }
            EstadoBadge(candidata.estado)
            WimCard {
                Text("Trayectoria", style = MaterialTheme.typography.headlineSmall)
                Text("Experiencia · " + candidata.anosExperiencia + " años", color = WimMuted)
                Spacer(Modifier.height(WimDesign.gap))
                Text(candidata.resumen)
            }
            WimCard {
                Text("Contacto y documentos", style = MaterialTheme.typography.titleMedium)
                listOf("Correo" to candidata.correo, "Teléfono" to candidata.telefono,
                    "Perfil profesional" to candidata.enlacePerfil, "CV" to candidata.uriCv).forEach { (label, value) ->
                    Spacer(Modifier.height(12.dp))
                    Text(label, style = MaterialTheme.typography.labelMedium, color = WimMuted)
                    Text(value)
                }
            }
            if (puedeEditar) {
                WimButton("Evaluar candidatura", onEvaluar, Modifier.fillMaxWidth())
                WimOutlinedButton("Editar candidatura", onEditar, Modifier.fillMaxWidth())
            }
            WimCard(onClick = { expanded = !expanded }) {
                Text("Historial · " + historial.size, style = MaterialTheme.typography.titleMedium)
                AnimatedVisibility(expanded) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (historial.isEmpty()) Text("Sin actividad registrada")
                        historial.forEach {
                            HorizontalDivider()
                            Text(it.accion, style = MaterialTheme.typography.labelLarge)
                            Text(it.detalle)
                        }
                    }
                }
            }
        }
    }
}
