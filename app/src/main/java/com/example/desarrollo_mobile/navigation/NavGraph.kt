package com.example.desarrollo_mobile.navigation

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

@Composable
fun WimNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    sesionViewModel: SesionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    candidatasViewModel: CandidatasViewModel = viewModel(factory = AppViewModelProvider.Factory),
    evaluacionViewModel: EvaluacionViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val sesionState by sesionViewModel.uiState.collectAsState()

    val onCerrarSesionLambda = {
        sesionViewModel.cerrarSesion()
        navController.navigate(Screen.Login.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginExitoso = { nombre, rol ->
                    sesionViewModel.iniciarSesion(nombre, rol)
                    if (rol == Rol.DIRECCION) {
                        navController.navigate(Screen.RankingConsolidado.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.ListaCandidatas.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Screen.ListaCandidatas.route) {
            val rolActual = sesionState.sesionActual?.rol ?: Rol.EVALUADORA
            ListaCandidatasScreen(
                viewModel = candidatasViewModel,
                rolActual = rolActual,
                onNuevaCandidataClick = {
                    navController.navigate(Screen.RegistroCandidata.route)
                },
                onCandidataClick = { candidataId ->
                    navController.navigate(Screen.EvaluarCandidata.crearRuta(candidataId))
                },
                onCerrarSesionClick = onCerrarSesionLambda
            )
        }

        composable(Screen.RegistroCandidata.route) {
            val usuarioNombre = sesionState.sesionActual?.nombreUsuario ?: "Evaluadora"
            val usuarioRol = sesionState.sesionActual?.rol?.etiqueta ?: Rol.EVALUADORA.etiqueta
            RegistroCandidataScreen(
                viewModel = candidatasViewModel,
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
                viewModel = evaluacionViewModel,
                evaluadoraClave = evaluadoraClave,
                evaluadoraNombre = evaluadoraNombre,
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.RankingConsolidado.route) {
            RankingScreen(
                viewModel = candidatasViewModel,
                onCerrarSesionClick = onCerrarSesionLambda
            )
        }
    }
}
