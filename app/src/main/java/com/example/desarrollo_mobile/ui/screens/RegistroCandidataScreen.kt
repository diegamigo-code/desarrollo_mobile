package com.example.desarrollo_mobile.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.desarrollo_mobile.model.Candidata
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
        topBar = {
            TopAppBar(
                title = { Text("Registrar Candidata") },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { viewModel.onFormCambiado(nombre = it) },
                label = { Text("Nombre Completo") },
                isError = state.errorNombre != null,
                supportingText = { state.errorNombre?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.correo,
                onValueChange = { viewModel.onFormCambiado(correo = it) },
                label = { Text("Correo Electrónico (@ejemplo.cl, @example.com, @test.cl)") },
                isError = state.errorCorreo != null,
                supportingText = { state.errorCorreo?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.telefono,
                onValueChange = { viewModel.onFormCambiado(telefono = it) },
                label = { Text("Teléfono (+56 9 XXXX XXXX)") },
                isError = state.errorTelefono != null,
                supportingText = { state.errorTelefono?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = menuAreaExpanded,
                onExpandedChange = { menuAreaExpanded = !menuAreaExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.area,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Área de Especialidad") },
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
                            text = { Text(areaOption) },
                            onClick = {
                                viewModel.onFormCambiado(area = areaOption)
                                menuAreaExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.anosExperiencia,
                onValueChange = { viewModel.onFormCambiado(anosExperiencia = it) },
                label = { Text("Años de Experiencia") },
                isError = state.errorAnos != null,
                supportingText = { state.errorAnos?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.resumen,
                onValueChange = { viewModel.onFormCambiado(resumen = it) },
                label = { Text("Resumen Profesional (30-500 caracteres)") },
                isError = state.errorResumen != null,
                supportingText = { state.errorResumen?.let { Text(it) } },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.enlacePerfil,
                onValueChange = { viewModel.onFormCambiado(enlacePerfil = it) },
                label = { Text("Perfil LinkedIn (https://www.linkedin.com/in/...)") },
                isError = state.errorEnlace != null,
                supportingText = { state.errorEnlace?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.uriCv,
                onValueChange = { viewModel.onFormCambiado(uriCv = it) },
                label = { Text("Ruta / Nombre del archivo CV (PDF)") },
                isError = state.errorCv != null,
                supportingText = { state.errorCv?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            state.errorGeneral?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.registrarCandidata(usuarioNombre, usuarioRol) },
                enabled = !state.estaGuardando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.estaGuardando) "Guardando..." else "Guardar Candidata")
            }
        }
    }
}