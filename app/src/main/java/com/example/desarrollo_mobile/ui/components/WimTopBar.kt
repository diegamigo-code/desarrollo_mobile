package com.example.desarrollo_mobile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.ui.theme.*

@Composable
fun WimTopBar(title: String, subtitle: String? = null, onCerrarSesionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth().padding(horizontal = WimDesign.page, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            subtitle?.let { Text(it, style = MaterialTheme.typography.labelLarge, color = WimPrimary) }
            Text(title, style = MaterialTheme.typography.headlineLarge, color = WimText)
        }
        onCerrarSesionClick?.let { action ->
            OutlinedIconButton(onClick = action) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, "Cerrar sesión", tint = WimMuted)
            }
        }
    }
}
