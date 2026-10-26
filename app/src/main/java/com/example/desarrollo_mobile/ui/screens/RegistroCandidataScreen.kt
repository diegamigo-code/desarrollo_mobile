package com.example.desarrollo_mobile.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.desarrollo_mobile.ui.components.WimButton
import com.example.desarrollo_mobile.ui.components.WimCard
import com.example.desarrollo_mobile.ui.components.WimTextField
import com.example.desarrollo_mobile.ui.theme.WimOutline
import com.example.desarrollo_mobile.ui.theme.WimPrimary
import com.example.desarrollo_mobile.ui.theme.WimPrimaryLight
import com.example.desarrollo_mobile.ui.theme.WimSecondary
import com.example.desarrollo_mobile.ui.theme.WimText
import com.example.desarrollo_mobile.ui.viewmodel.CandidatasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroCandidataScreen(
    viewModel: CandidatasViewModel,
    usuarioNombre: String,
    usuarioRol: String,
    onVolverClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.formState.collectAsState()
    var menuAreaExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(state.guardadoExitoso) {
        if (state.guardadoExitoso) {
            onVolverClick()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🌸", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Registro de Candidata",
                            fontWeight = FontWeight.Bold,
                            color = WimText
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onVolverClick,
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .background(WimPrimaryLight)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = WimPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // TARJETA HEADER PASTEL
            WimCard(
                borderColor = WimOutline,
                containerColor = Color.White
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = WimPrimaryLight,
                        shape = CircleShape,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = WimPrimary,
                            modifier = Modifier
                                .padding(12.dp)
                                .size(24.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Formulario de Postulación",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = WimText
                        )
                        Text(
                            text = "Registrado por $usuarioNombre ($usuarioRol)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SECCIÓN 1: DATOS PERSONALES
            WimCard(borderColor = WimOutline) {
                Text(
                    text = "DATOS DE LA POSTULANTE",
                    style = MaterialTheme.typography.labelSmall,
                    color = WimSecondary,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                WimTextField(
                    value = state.nombre,
                    onValueChange = { viewModel.onFormCambiado(nombre = it) },
                    label = "Nombre Completo",
                    isError = state.errorNombre != null,
                    supportingText = state.errorNombre?.let { err -> { Text(err, color = MaterialTheme.colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                WimTextField(
                    value = state.correo,
                    onValueChange = { viewModel.onFormCambiado(correo = it) },
                    label = "Correo Electrónico (@ejemplo.cl, @example.com, @test.cl)",
                    isError = state.errorCorreo != null,
                    supportingText = state.errorCorreo?.let { err -> { Text(err, color = MaterialTheme.colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                WimTextField(
                    value = state.telefono,
                    onValueChange = { viewModel.onFormCambiado(telefono = it) },
                    label = "Teléfono (+56 9 XXXX XXXX)",
                    isError = state.errorTelefono != null,
                    supportingText = state.errorTelefono?.let { err -> { Text(err, color = MaterialTheme.colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SECCIÓN 2: PERFIL PROFESIONAL
            WimCard(borderColor = WimOutline) {
                Text(
                    text = "PERFIL PROFESIONAL",
                    style = MaterialTheme.typography.labelSmall,
                    color = WimSecondary,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = menuAreaExpanded,
                    onExpandedChange = { menuAreaExpanded = !menuAreaExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WimTextField(
                        value = state.area,
                        onValueChange = {},
                        label = "Área de Especialidad",
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuAreaExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = menuAreaExpanded,
                        onDismissRequest = { menuAreaExpanded = false }
                    ) {
                        Candidata.AREAS.forEach { areaOption ->
                            DropdownMenuItem(
                                text = { Text(areaOption, fontWeight = FontWeight.Medium) },
                                onClick = {
                                    viewModel.onFormCambiado(area = areaOption)
                                    menuAreaExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                WimTextField(
                    value = state.anosExperiencia,
                    onValueChange = { viewModel.onFormCambiado(anosExperiencia = it) },
                    label = "Años de Experiencia (0-50)",
                    isError = state.errorAnos != null,
                    supportingText = state.errorAnos?.let { err -> { Text(err, color = MaterialTheme.colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                WimTextField(
                    value = state.resumen,
                    onValueChange = { viewModel.onFormCambiado(resumen = it) },
                    label = "Resumen Profesional (30-500 caracteres)",
                    singleLine = false,
                    isError = state.errorResumen != null,
                    supportingText = state.errorResumen?.let { err -> { Text(err, color = MaterialTheme.colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SECCIÓN 3: DOCUMENTOS Y ENLACES
            WimCard(borderColor = WimOutline) {
                Text(
                    text = "DOCUMENTACIÓN ADJUNTA",
                    style = MaterialTheme.typography.labelSmall,
                    color = WimSecondary,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                WimTextField(
                    value = state.enlacePerfil,
                    onValueChange = { viewModel.onFormCambiado(enlacePerfil = it) },
                    label = "Perfil LinkedIn (https://www.linkedin.com/in/...)",
                    isError = state.errorEnlace != null,
                    supportingText = state.errorEnlace?.let { err -> { Text(err, color = MaterialTheme.colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                WimTextField(
                    value = state.uriCv,
                    onValueChange = { viewModel.onFormCambiado(uriCv = it) },
                    label = "Ruta / Archivo CV (ej: cv_maria.pdf)",
                    isError = state.errorCv != null,
                    supportingText = state.errorCv?.let { err -> { Text(err, color = MaterialTheme.colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            state.errorGeneral?.let { err ->
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = err,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            WimButton(
                text = if (state.estaGuardando) "Guardando..." else "Guardar Candidata  ✨",
                onClick = { viewModel.registrarCandidata(usuarioNombre, usuarioRol) },
                enabled = !state.estaGuardando,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
