package com.example.desarrollo_mobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.Sesion
import com.example.desarrollo_mobile.ui.components.*
import com.example.desarrollo_mobile.ui.theme.*

@Composable
fun PerfilScreen(sesion: Sesion, salir: () -> Unit) {
    val selected = LocalAppearance.current
    val select = LocalSetAppearance.current
    Column(Modifier.statusBarsPadding().verticalScroll(rememberScrollState()).padding(WimDesign.page),
        verticalArrangement = Arrangement.spacedBy(WimDesign.gap)) {
        Text("Mi cuenta", style = MaterialTheme.typography.headlineLarge)
        WimCover(sesion.rol.etiqueta, sesion.nombreUsuario)
        WimCard {
            Text("Apariencia", style = MaterialTheme.typography.headlineSmall)
            Column(Modifier.selectableGroup()) {
                Appearance.entries.forEach { mode ->
                    Row(Modifier.fillMaxWidth().heightIn(min = 52.dp)
                        .selectable(mode == selected, role = Role.RadioButton, onClick = { select(mode) }),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(if (mode == Appearance.Automático) "Seguir tema del sistema" else mode.name,
                            modifier = Modifier.weight(1f))
                        RadioButton(selected = mode == selected, onClick = null)
                    }
                }
            }
        }
        WimOutlinedButton("Cerrar sesión", salir, Modifier.fillMaxWidth())
    }
}
