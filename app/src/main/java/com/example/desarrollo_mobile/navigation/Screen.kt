package com.example.desarrollo_mobile.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object ListaCandidatas : Screen("lista_candidatas")
    object RegistroCandidata : Screen("registro_candidata")
    object EvaluarCandidata : Screen("evaluar_candidata/{candidataId}") {
        fun crearRuta(candidataId: Long) = "evaluar_candidata/$candidataId"
    }
    object DetalleCandidata : Screen("detalle_candidata/{candidataId}") {
        fun crearRuta(candidataId: Long) = "detalle_candidata/$candidataId"
    }
    object RankingConsolidado : Screen("ranking_consolidado")
}