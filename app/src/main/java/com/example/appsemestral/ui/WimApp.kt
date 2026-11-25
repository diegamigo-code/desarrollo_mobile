package com.example.appsemestral.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appsemestral.nativo.SolicitudPermisoNotificaciones
import com.example.appsemestral.navigation.DestinoPrincipal
import com.example.appsemestral.navigation.Rutas
import com.example.appsemestral.navigation.WimNavHost
import com.example.appsemestral.ui.viewmodel.AppViewModelProvider
import com.example.appsemestral.ui.viewmodel.SesionViewModel
import kotlinx.coroutines.launch

/** Composable raíz: estructura general, barra inferior animada y mensajes (Snackbar). */
@Composable
fun WimApp() {
    val navController = rememberNavController()
    // ViewModel de sesión a nivel de Activity: lo comparten todas las pantallas
    val sesionViewModel: SesionViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val sesion by sesionViewModel.sesion.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val mostrarMensaje = remember<(String) -> Unit>(scope, snackbarHostState) {
        { mensaje ->
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(mensaje)
            }
            Unit
        }
    }

    // Token rechazado por el backend (401): volver al login desde cualquier pantalla
    LaunchedEffect(sesionViewModel) {
        sesionViewModel.sesionExpirada.collect {
            navController.navigate(Rutas.LOGIN) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
            mostrarMensaje("Tu sesión expiró. Vuelve a ingresar")
        }
    }

    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route
    val mostrarBarra = sesion != null && DestinoPrincipal.entries.any { it.ruta == rutaActual }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            AnimatedVisibility(
                visible = mostrarBarra,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                NavigationBar(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).clip(RoundedCornerShape(28.dp)),
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    tonalElevation = 0.dp
                ) {
                    DestinoPrincipal.entries.forEach { destino ->
                        val active = rutaActual == destino.ruta
                        val scale by animateFloatAsState(if (active) 1.15f else 1f, spring(dampingRatio = 0.55f), label = "iconScale")
                        val lift by animateFloatAsState(if (active) -3f else 0f, label = "iconLift")
                        NavigationBarItem(
                            selected = rutaActual == destino.ruta,
                            onClick = {
                                navController.navigate(destino.ruta) {
                                    popUpTo(Rutas.CANDIDATAS) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(destino.icono, contentDescription = null, modifier = Modifier.graphicsLayer { scaleX = scale; scaleY = scale; translationY = lift.dp.toPx() }) },
                            label = { Text(destino.etiqueta) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        WimNavHost(
            navController = navController,
            sesionViewModel = sesionViewModel,
            sesion = sesion,
            mostrarMensaje = mostrarMensaje,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
        )
    }

    // Recurso nativo: se solicita el permiso de notificaciones una vez iniciada la sesión
    if (sesion != null) {
        SolicitudPermisoNotificaciones(mostrarMensaje)
    }
}
