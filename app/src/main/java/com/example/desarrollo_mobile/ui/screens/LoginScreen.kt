package com.example.desarrollo_mobile.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.Rol
import com.example.desarrollo_mobile.ui.components.WimButton
import com.example.desarrollo_mobile.ui.components.WimCard
import com.example.desarrollo_mobile.ui.components.WimOutlinedButton
import com.example.desarrollo_mobile.ui.components.WimTextField
import com.example.desarrollo_mobile.ui.theme.WimHeroGradient
import com.example.desarrollo_mobile.ui.theme.WimOutline
import com.example.desarrollo_mobile.ui.theme.WimPrimary
import com.example.desarrollo_mobile.ui.theme.WimPrimaryLight
import com.example.desarrollo_mobile.ui.theme.WimSecondary
import com.example.desarrollo_mobile.ui.theme.WimText

@Composable
fun LoginScreen(
    onLoginExitoso: (String, Rol) -> Unit,
    modifier: Modifier = Modifier
) {
    var nombreUsuario by remember { mutableStateOf("") }
    var rolSeleccionado by remember { mutableStateOf(Rol.EVALUADORA) }
    var errorNombre by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val trelloUrl = "https://trello.com/b/jEJZOR8N/proyecto-wim-100-dsy1105"

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // HEADER BRANDING DE BIENVENIDA CON ÍCONO FLOTANTE
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(WimHeroGradient)
            ) {
                Text(text = "🌸", style = MaterialTheme.typography.headlineMedium)
            }

            Spacer(Modifier.height(16.dp))

            Surface(
                color = WimPrimaryLight,
                shape = CircleShape,
                border = BorderStroke(1.dp, WimPrimary.copy(alpha = 0.3f))
            ) {
                Text(
                    text = "✨ WIM 100 · TALENT HUB",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = WimPrimary,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = "El Talento del Futuro Empieza Aquí",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = WimText
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Plataforma oficial de evaluación y selección de talentos femeninos en Tecnología e Ingeniería.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(28.dp))

            // TARJETA PRINCIPAL DE ACCESO PASTEL
            WimCard(
                borderColor = WimOutline,
                elevation = 4.dp
            ) {
                Text(
                    text = "Acceso a la Plataforma",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = WimText
                )
                Text(
                    text = "Ingresa tus credenciales para continuar",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(20.dp))

                WimTextField(
                    value = nombreUsuario,
                    onValueChange = {
                        nombreUsuario = it
                        errorNombre = null
                    },
                    label = "Nombre de usuario",
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorNombre != null,
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null, tint = WimPrimary)
                    }
                )

                if (errorNombre != null) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = errorNombre!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "ROL DE ACCESO",
                    style = MaterialTheme.typography.labelSmall,
                    color = WimSecondary,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(Modifier.height(8.dp))

                // TOGGLE CONMUTADOR PASTEL DE ROLES
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(4.dp)
                ) {
                    Rol.entries.forEach { rol ->
                        val selected = rolSeleccionado == rol
                        val bgAnim by animateColorAsState(
                            targetValue = if (selected) WimPrimary else Color.Transparent,
                            label = "RolBgAnim"
                        )
                        val textAnim by animateColorAsState(
                            targetValue = if (selected) Color.White else WimText,
                            label = "RolTextAnim"
                        )

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .clip(CircleShape)
                                .background(bgAnim)
                                .clickable { rolSeleccionado = rol }
                                .padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = if (rol == Rol.EVALUADORA) "👩‍💼 Evaluadora" else "👑 Dirección",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                color = textAnim
                            )
                        }
                    }
                }

                Spacer(Modifier.height(22.dp))

                WimButton(
                    text = "Ingresar a la Plataforma",
                    onClick = {
                        if (nombreUsuario.isBlank()) {
                            errorNombre = "Por favor ingresa tu nombre de usuario"
                        } else {
                            onLoginExitoso(nombreUsuario.trim(), rolSeleccionado)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    icon = {
                        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
                    }
                )

                Spacer(Modifier.height(12.dp))

                WimOutlinedButton(
                    text = "📋 Ver Tablero Trello del Proyecto",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trelloUrl))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🌸", style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "ENTORNO SEGURO WIM 100",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
