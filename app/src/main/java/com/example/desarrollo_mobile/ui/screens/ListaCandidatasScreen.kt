package com.example.desarrollo_mobile.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.Candidata
import com.example.desarrollo_mobile.model.EstadoCandidatura
import com.example.desarrollo_mobile.model.Rol
import com.example.desarrollo_mobile.ui.components.EstadoBadge
import com.example.desarrollo_mobile.ui.components.WimCard
import com.example.desarrollo_mobile.ui.components.WimTextField
import com.example.desarrollo_mobile.ui.components.WimTopBar
import com.example.desarrollo_mobile.ui.theme.WimHeroGradient
import com.example.desarrollo_mobile.ui.theme.WimOutline
import com.example.desarrollo_mobile.ui.theme.WimPrimary
import com.example.desarrollo_mobile.ui.theme.WimPrimaryLight
import com.example.desarrollo_mobile.ui.theme.WimSecondary
import com.example.desarrollo_mobile.ui.theme.WimSecondaryLight
import com.example.desarrollo_mobile.ui.theme.WimTertiaryLight
import com.example.desarrollo_mobile.ui.theme.WimText
import com.example.desarrollo_mobile.ui.viewmodel.CandidatasViewModel

@Composable
fun ListaCandidatasScreen(
    viewModel: CandidatasViewModel,
    rolActual: Rol,
    onNuevaCandidataClick: () -> Unit,
    onCandidataClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onCerrarSesionClick: (() -> Unit)? = null
) {
    val candidatas by viewModel.candidatasFiltradas.collectAsState()
    val query by viewModel.busquedaQuery.collectAsState()
    var areaFiltroSeleccionada by remember { mutableStateOf<String?>(null) }

    val candidatasMostradas: List<Candidata> = candidatas.filter { cand ->
        val tag = areaFiltroSeleccionada
        tag == null || cand.area.contains(tag, ignoreCase = true) || cand.estado.name.equals(tag, ignoreCase = true)
    }

    val totalEnEvaluacion = candidatas.count { it.estado == EstadoCandidatura.EN_EVALUACION }
    val totalSeleccionadas = candidatas.count { it.estado == EstadoCandidatura.SELECCIONADA }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Nueva Candidata", fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Add, contentDescription = "Registrar candidata") },
                onClick = onNuevaCandidataClick,
                containerColor = WimPrimary,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            WimTopBar(
                title = "Talent Dashboard",
                subtitle = "Panel Principal · ${rolActual.etiqueta}",
                onCerrarSesionClick = onCerrarSesionClick
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // 1. BANNER HERO DE BIENVENIDA (PANTALLA PRINCIPAL)
                item {
                    Surface(
                        shape = RoundedCornerShape(28.dp),
                        border = BorderStroke(1.dp, WimOutline),
                        shadowElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .background(WimHeroGradient)
                                .padding(22.dp)
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Surface(
                                        color = Color.White.copy(alpha = 0.85f),
                                        shape = CircleShape,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = "✨ WIM 100 TALENT HUB",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = WimPrimary,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                    Text(text = "🌸", style = MaterialTheme.typography.titleLarge)
                                }

                                Text(
                                    text = "Impulsando el Talento Femenino en Tecnología",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = WimText
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Evalúa, gestiona y selecciona a las mejores postulantes con transparencia y objetividad.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = WimText.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }

                // 2. FILA DE TARJETAS DE ESTADÍSTICAS ANIMADAS
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Stat 1: Total
                        WimCard(
                            containerColor = WimPrimaryLight,
                            borderColor = WimPrimary.copy(alpha = 0.3f),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("👥", style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.width(6.dp))
                                Text("TOTAL", style = MaterialTheme.typography.labelSmall, color = WimPrimary, fontWeight = FontWeight.Bold)
                            }
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = candidatas.size.toString(),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Black,
                                color = WimPrimary
                            )
                        }

                        // Stat 2: En Evaluación
                        WimCard(
                            containerColor = WimTertiaryLight,
                            borderColor = Color(0xFFFFB74D).copy(alpha = 0.4f),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("⏳", style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.width(6.dp))
                                Text("EN EVAL", style = MaterialTheme.typography.labelSmall, color = Color(0xFFD97706), fontWeight = FontWeight.Bold)
                            }
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = totalEnEvaluacion.toString(),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFD97706)
                            )
                        }

                        // Stat 3: Seleccionadas
                        WimCard(
                            containerColor = WimSecondaryLight,
                            borderColor = WimSecondary.copy(alpha = 0.4f),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🏆", style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.width(6.dp))
                                Text("ELEGIDAS", style = MaterialTheme.typography.labelSmall, color = WimSecondary, fontWeight = FontWeight.Bold)
                            }
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = totalSeleccionadas.toString(),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Black,
                                color = WimSecondary
                            )
                        }
                    }
                }

                // 3. BARRA DE BÚSQUEDA
                item {
                    WimTextField(
                        value = query,
                        onValueChange = viewModel::onBusquedaCambiada,
                        label = "Buscar por nombre, correo o especialidad...",
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Buscar", tint = WimPrimary)
                        },
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { viewModel.onBusquedaCambiada("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Limpiar", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    )
                }

                // 4. CHIPS DE FILTRO RÁPIDO
                item {
                    val categorias = listOf(
                        "Todas" to null,
                        "💻 Ciberseguridad" to "Ciberseguridad",
                        "🚀 Software" to "Desarrollo",
                        "⚡ Infraestructura" to "Infraestructura",
                        "📊 Ciencia de Datos" to "Ciencia de Datos",
                        "⏳ En Evaluación" to "EN_EVALUACION",
                        "🏆 Seleccionadas" to "SELECCIONADA"
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categorias) { (label, tag) ->
                            val selected = areaFiltroSeleccionada == tag
                            Surface(
                                color = if (selected) WimPrimary else Color.White,
                                contentColor = if (selected) Color.White else WimText,
                                border = BorderStroke(1.dp, if (selected) WimPrimary else WimOutline),
                                shape = CircleShape,
                                modifier = Modifier.clickable {
                                    areaFiltroSeleccionada = if (selected) null else tag
                                }
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                // 5. ENCABEZADO DIRECTORIO
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Directorio de Postulantes",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Surface(
                            color = WimPrimaryLight,
                            shape = CircleShape
                        ) {
                            Text(
                                text = "${candidatasMostradas.size} candidatas",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = WimPrimary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // 6. LISTA DE CANDIDATAS CON ANIMACIÓN
                if (candidatasMostradas.isEmpty()) {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🌸", style = MaterialTheme.typography.headlineLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No encontramos perfiles que coincidan",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Prueba con otra búsqueda o agrega una nueva candidata.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                } else {
                    items(candidatasMostradas, key = { cand -> cand.id }) { candidata ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + slideInVertically(initialOffsetY = { 40 }),
                            exit = fadeOut()
                        ) {
                            WimCard(
                                onClick = { onCandidataClick(candidata.id) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    // Avatar Iniciales con gradiente pastel
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(52.dp)
                                            .clip(CircleShape)
                                            .background(WimHeroGradient)
                                    ) {
                                        val iniciales = candidata.nombre
                                            .trim()
                                            .split(" ")
                                            .take(2)
                                            .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                                            .joinToString("")

                                        Text(
                                            text = iniciales.ifEmpty { "W" },
                                            color = WimPrimary,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    }

                                    // Info de la candidata
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = candidata.nombre,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = WimText
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Surface(
                                            color = WimSecondaryLight,
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.padding(vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = candidata.area,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = WimSecondary,
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "⭐ ${candidata.anosExperiencia} años de trayectoria",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Pie de tarjeta con Estado y acción
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    EstadoBadge(estado = candidata.estado)

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "VER PERFIL",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = WimPrimary
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                            contentDescription = null,
                                            tint = WimPrimary,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Espacio inferior para el FAB
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}
