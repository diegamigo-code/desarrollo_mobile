package com.example.desarrollo_mobile.ui.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.R
import com.example.desarrollo_mobile.model.AccesoDemo
import com.example.desarrollo_mobile.ui.viewmodel.SesionUiState
import com.example.desarrollo_mobile.ui.components.WimButton
import com.example.desarrollo_mobile.ui.components.WimCard
import com.example.desarrollo_mobile.ui.theme.*

@Composable
fun LoginScreen(estado: SesionUiState, onLogin: (String, String) -> Unit, modifier: Modifier = Modifier) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var mostrarClave by remember { mutableStateOf(false) }
    var errorTablero by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }
    val alpha by animateFloatAsState(if (visible) 1f else 0f, tween(WimDesign.motionMillis), label = "Marca")
    val context = LocalContext.current
    Column(modifier.fillMaxSize().background(WimBackground).systemBarsPadding().imePadding()
        .verticalScroll(rememberScrollState()).padding(WimDesign.page),
        verticalArrangement = Arrangement.spacedBy(WimDesign.gap)) {
        Box(Modifier.graphicsLayer { this.alpha = alpha }) {
            com.example.desarrollo_mobile.ui.components.WimCover("WIM 100", "Selección de talento")
        }
        WimCard {
            Text("Iniciar sesión", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(WimDesign.gap))
            OutlinedTextField(nombre, { nombre = it }, Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                label = { Text("Usuario") }, singleLine = true)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(clave, { clave = it }, Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Password),
                label = { Text("Contraseña") }, singleLine = true,
                visualTransformation = if (mostrarClave) androidx.compose.ui.text.input.VisualTransformation.None
                    else androidx.compose.ui.text.input.PasswordVisualTransformation(),
                trailingIcon = { TextButton(onClick = { mostrarClave = !mostrarClave }) { Text(if (mostrarClave) "Ocultar" else "Ver") } })
            Spacer(Modifier.height(WimDesign.gap))
            estado.error?.let { Text(it, color = WimDanger) }
            WimButton(if (estado.ocupada) "Conectando…" else "Continuar", { onLogin(nombre.trim(), clave) },
                Modifier.fillMaxWidth(), enabled = !estado.ocupada && nombre.isNotBlank() && clave.isNotBlank())
        }
        Text("Cuentas de demostración", style = MaterialTheme.typography.titleSmall, color = WimMuted)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AccesoDemo.entries.forEach { cuenta ->
                OutlinedButton(onClick = { nombre = cuenta.usuario; clave = cuenta.clave }) {
                    Text(cuenta.rol.etiqueta)
                }
            }
        }
        TextButton(onClick = {
            try {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://trello.com/b/jEJZOR8N/proyecto-wim-100-dsy1105")))
            } catch (_: ActivityNotFoundException) { errorTablero = true }
        }) { Text("Abrir tablero de Trello") }
        if (errorTablero) Text("No hay una aplicación disponible para abrir el tablero.", color = WimDanger)
    }
}
