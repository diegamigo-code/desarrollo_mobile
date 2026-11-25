package com.example.desarrollo_mobile.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import kotlinx.coroutines.flow.map
import com.example.desarrollo_mobile.ui.screens.CandidataDetalle
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.desarrollo_mobile.ui.theme.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.desarrollo_mobile.model.Rol
import com.example.desarrollo_mobile.ui.screens.EvaluarCandidataScreen
import com.example.desarrollo_mobile.ui.screens.ListaCandidatasScreen
import com.example.desarrollo_mobile.ui.screens.LoginScreen
import com.example.desarrollo_mobile.ui.screens.RankingScreen
import com.example.desarrollo_mobile.ui.screens.RegistroCandidataScreen
import com.example.desarrollo_mobile.ui.viewmodel.AppViewModelProvider
import com.example.desarrollo_mobile.ui.viewmodel.CandidatasViewModel
import com.example.desarrollo_mobile.ui.viewmodel.EvaluacionViewModel
import com.example.desarrollo_mobile.ui.viewmodel.SesionViewModel

@OptIn(androidx.compose.animation.ExperimentalSharedTransitionApi::class)
@Composable
fun WimNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    sesionViewModel: SesionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    candidatasViewModel: CandidatasViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val sesionState by sesionViewModel.uiState.collectAsState()

    val onCerrarSesionLambda = {
        sesionViewModel.cerrarSesion()
        navController.navigate(Screen.Login.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    val entry by navController.currentBackStackEntryAsState()
    val rutaActual = entry?.destination?.route
    var reintento by remember { mutableIntStateOf(0) }
    LaunchedEffect(rutaActual, sesionState.estaAutenticado, reintento) {
        if (rutaActual != null && rutaActual != Screen.Login.route) {
            if (sesionState.estaAutenticado) sesionViewModel.refrescar()
            else navController.navigate(Screen.Login.route) { popUpTo(0) { inclusive = true } }
        }
    }
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        Column {
            if (sesionState.estaAutenticado && sesionState.error != null) {
                Text(sesionState.error.orEmpty(), modifier = Modifier.statusBarsPadding())
                TextButton(onClick = { reintento++ }) { Text("Reintentar") }
            }
            androidx.compose.animation.SharedTransitionLayout(Modifier.weight(1f)) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.route,
                    enterTransition = { fadeIn(animationSpec = tween(WimDesign.motionMillis)) + slideInHorizontally(initialOffsetX = { 300 }) },
                    exitTransition = { fadeOut(animationSpec = tween(WimDesign.motionMillis)) + slideOutHorizontally(targetOffsetX = { -300 }) },
                    popEnterTransition = { fadeIn(animationSpec = tween(WimDesign.motionMillis)) + slideInHorizontally(initialOffsetX = { -300 }) },
                    popExitTransition = { fadeOut(animationSpec = tween(WimDesign.motionMillis)) + slideOutHorizontally(targetOffsetX = { 300 }) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(Screen.Login.route) {
                        LaunchedEffect(sesionState.estaAutenticado) {
                            if (sesionState.estaAutenticado) navController.navigate(Screen.Dashboard.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                        LoginScreen(sesionState, sesionViewModel::iniciarSesion)
                    }

                    composable(Screen.Dashboard.route) {
                        val agenda by candidatasViewModel.agenda.collectAsState()
                        Column(Modifier.statusBarsPadding().verticalScroll(rememberScrollState()).padding(WimDesign.page),
                            verticalArrangement = Arrangement.spacedBy(WimDesign.gap)) {
                            val rol = sesionState.sesionActual?.rol
                            Text(rol?.etiqueta.orEmpty(), style = MaterialTheme.typography.labelLarge, color = WimPrimary)
                            Text("Resumen de candidaturas", style = MaterialTheme.typography.headlineLarge)
                            val datos = agenda
                            if (datos == null) LinearProgressIndicator()
                            else if (rol != null) com.example.desarrollo_mobile.ui.screens.DashboardContent(datos, rol,
                                abrir = { navController.navigate(Screen.DetalleCandidata.crearRuta(it)) },
                                registrar = { navController.navigate(Screen.RegistroCandidata.route) },
                                ranking = { navController.navigate(Screen.RankingConsolidado.route) })
                        }
                    }

                    composable(Screen.ListaCandidatas.route) {
                        val rolActual = sesionState.sesionActual?.rol ?: Rol.EVALUADORA
                        ListaCandidatasScreen(
                            viewModel = candidatasViewModel,
                            rolActual = rolActual,
                            titleModifier = { id -> Modifier.sharedElement(rememberSharedContentState("title-$id"), this@composable) },
                            onNuevaCandidataClick = {
                                navController.navigate(Screen.RegistroCandidata.route)
                            },
                            onCandidataClick = { candidataId ->
                                navController.navigate(Screen.DetalleCandidata.crearRuta(candidataId))
                            },
                            onCerrarSesionClick = onCerrarSesionLambda
                        )
                    }

                    composable(Screen.RegistroCandidata.route + "?editar={editar}", arguments = listOf(
                        navArgument("editar") { type = NavType.LongType; defaultValue = 0L }
                    )) { back ->
                        val form: CandidatasViewModel = viewModel(factory = AppViewModelProvider.Factory)
                        val editar = back.arguments?.getLong("editar") ?: 0L
                        if (editar != 0L && !form.esEdicion) {
                            val original by remember(editar) { form.candidata(editar) }.collectAsState(null)
                            LaunchedEffect(original) { original?.let(form::prepararFormulario) }
                            LinearProgressIndicator()
                            if (original == null || !form.esEdicion) return@composable
                        }
                        val usuarioNombre = sesionState.sesionActual?.nombreUsuario ?: "Evaluadora"
                        val usuarioRol = sesionState.sesionActual?.rol?.etiqueta ?: Rol.EVALUADORA.etiqueta
                        RegistroCandidataScreen(
                            viewModel = form,
                            usuarioNombre = usuarioNombre,
                            usuarioRol = usuarioRol,
                            onVolverClick = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(
                        route = Screen.EvaluarCandidata.route,
                        arguments = listOf(navArgument("candidataId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val candidataId = backStackEntry.arguments?.getLong("candidataId") ?: 0L
                        val evaluadoraNombre = sesionState.sesionActual?.nombreUsuario ?: "Evaluadora"
                        val evaluadoraClave = evaluadoraNombre.lowercase().replace(" ", "_")

                        EvaluarCandidataScreen(
                            candidataId = candidataId,
                            viewModel = viewModel(factory = AppViewModelProvider.Factory),
                            evaluadoraClave = evaluadoraClave,
                            evaluadoraNombre = evaluadoraNombre,
                            onVolverClick = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(Screen.DetalleCandidata.route, arguments = listOf(navArgument("candidataId") { type = NavType.LongType })) { back ->
                        val id = back.arguments!!.getLong("candidataId")
                        val detalle by remember(id) { candidatasViewModel.candidata(id).map { true to it } }
                            .collectAsState(false to null)
                        val historial by remember(id) { candidatasViewModel.historial(id) }.collectAsState(emptyList())
                        CandidataDetalle(detalle.first, detalle.second, historial,
                            Modifier.sharedElement(rememberSharedContentState("title-$id"), this@composable),
                            puedeEditar = sesionState.sesionActual?.rol == Rol.EVALUADORA,
                            onBack = { navController.popBackStack() },
                            onEvaluar = { navController.navigate(Screen.EvaluarCandidata.crearRuta(id)) },
                            onEditar = { navController.navigate(Screen.RegistroCandidata.route + "?editar=$id") })
                    }
                    composable(Screen.Perfil.route) {
                        sesionState.sesionActual?.let {
                            com.example.desarrollo_mobile.ui.screens.PerfilScreen(it, onCerrarSesionLambda)
                        }
                    }
                    composable(Screen.RankingConsolidado.route) {
                        RankingScreen(
                            viewModel = candidatasViewModel,
                            onCerrarSesionClick = onCerrarSesionLambda
                        )
                    }
                }
            }
            val trabajo = if (sesionState.sesionActual?.rol == Rol.DIRECCION)
                Screen.RankingConsolidado.route to "Ranking" else Screen.ListaCandidatas.route to "Candidaturas"
            val destinos = listOf(Screen.Dashboard.route to "Dashboard", trabajo, Screen.Perfil.route to "Perfil")
            val actual = entry?.destination?.route
            if (sesionState.estaAutenticado && actual != null && destinos.any { it.first == actual }) {
                com.example.desarrollo_mobile.ui.components.WimBottomBar(actual, destinos) { ruta ->
                    if (ruta != actual) navController.navigate(ruta) {
                        popUpTo(Screen.Dashboard.route)
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}
