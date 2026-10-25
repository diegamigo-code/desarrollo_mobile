package com.example.desarrollo_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.Rol
import com.example.desarrollo_mobile.ui.components.WimButton
import com.example.desarrollo_mobile.ui.components.WimCard
import com.example.desarrollo_mobile.ui.components.WimTextField
import com.example.desarrollo_mobile.ui.theme.WimCyan

@Composable
fun LoginScreen(onLoginExitoso: (String, Rol) -> Unit, modifier: Modifier = Modifier) {
    var nombreUsuario by remember { mutableStateOf("") }
    var rolSeleccionado by remember { mutableStateOf(Rol.EVALUADORA) }
    var errorNombre by remember { mutableStateOf<String?>(null) }

    Scaffold(containerColor = MaterialTheme.colorScheme.background, modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 24.dp, vertical = 22.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text("WIM 100  /  TALENT HUB", style = MaterialTheme.typography.labelMedium, color = WimCyan, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(22.dp))
            Text("El talento del " +
                    "futuro empieza aquí.", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(12.dp))
            Text("Plataforma de gestión y evaluación de candidatas.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(28.dp))
            WimCard {
                Text("Acceso a la plataforma", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text("Ingresa tus datos para continuar", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(18.dp))
                WimTextField(
                    value = nombreUsuario,
                    onValueChange = { nombreUsuario = it; errorNombre = null },
                    label = "Nombre de usuario",
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorNombre != null
                )
                if (errorNombre != null) {
                    Spacer(Modifier.height(4.dp))
                    Text(errorNombre!!, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                }
                Spacer(Modifier.height(18.dp))
                Text("PERFIL DE ACCESO", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                Rol.entries.forEach { rol ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        RadioButton(selected = rolSeleccionado == rol, onClick = { rolSeleccionado = rol })
                        Text(rol.etiqueta, modifier = Modifier.padding(start = 6.dp), color = MaterialTheme.colorScheme.onSurface)
                    }
                }
                Spacer(Modifier.height(12.dp))
                WimButton(
                    text = "Ingresar a la plataforma  →",
                    onClick = {
                        if (nombreUsuario.isBlank()) errorNombre = "El nombre de usuario es obligatorio"
                        else onLoginExitoso(nombreUsuario.trim(), rolSeleccionado)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("●", color = WimCyan)
                Text("  ENTORNO DE GESTIÓN WIM 100", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
