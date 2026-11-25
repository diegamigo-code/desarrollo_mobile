package com.example.appsemestral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.appsemestral.nativo.BiometriaHelper
import com.example.appsemestral.ui.components.sacudir
import com.example.appsemestral.util.buscarFragmentActivity
import com.example.appsemestral.ui.viewmodel.SesionViewModel

/** Ingreso: credenciales del servidor y verificación local del dispositivo. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: SesionViewModel,
    mostrarMensaje: (String) -> Unit,
    onIngreso: () -> Unit
) {
    val estado by viewModel.loginState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val disponibilidad = remember { BiometriaHelper.disponibilidad(context) }

    val ingresar: () -> Unit = {
        if (viewModel.validar()) {
            val activity = context.buscarFragmentActivity()
            if (disponibilidad == BiometriaHelper.Disponibilidad.DISPONIBLE && activity != null) {
                BiometriaHelper.autenticar(
                    activity = activity,
                    titulo = "Verifica tu identidad",
                    subtitulo = "Acceso a WIM 100",
                    onExito = {
                        viewModel.iniciarSesion("verificación del dispositivo", onIngreso)
                    },
                    onError = { mostrarMensaje("Verificación no completada: $it") }
                )
            } else {
                viewModel.iniciarSesion("credenciales del servidor", onIngreso)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            com.example.appsemestral.ui.components.MarcaWim() 
            Spacer(Modifier.height(16.dp))
            Text(
                "WIM 100",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "LIDERAZGO · TALENTO · FUTURO",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(28.dp))

            ElevatedCard(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth() // adaptable a tablets
            ) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Tu próximo impacto empieza aquí", style = MaterialTheme.typography.titleLarge)

                    OutlinedTextField(
                        value = estado.usuario,
                        onValueChange = viewModel::onUsuario,
                        label = { Text("Usuario") },
                        singleLine = true,
                        enabled = !estado.ingresando,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = estado.password,
                        onValueChange = viewModel::onPassword,
                        label = { Text("Contraseña") },
                        singleLine = true,
                        enabled = !estado.ingresando,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    estado.error?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }

                    Button(
                        onClick = ingresar,
                        enabled = !estado.ingresando,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .sacudir(estado.intentosFallidos)
                    ) {
                        Icon(Icons.Filled.Fingerprint, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(if (estado.ingresando) "Verificando…" else "Acceder al espacio")
                    }
                    Text(
                        "Verificación del dispositivo: ${disponibilidad.etiqueta}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "MVP académico · Solo datos ficticios",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

