package com.example.desarrollo_mobile.model

object CalculadoraPuntaje {

    fun calcularPuntajePonderado(
        puntajesConPeso: List<Pair<Int, Int>> // Lista de (Puntaje 1 a 5, Peso %)
    ): Double {
        if (puntajesConPeso.isEmpty()) return 0.0

        val pesoTotal = puntajesConPeso.sumOf { it.second }
        if (pesoTotal == 0) return 0.0

        val sumaPonderada = puntajesConPeso.sumOf { (puntaje, peso) -> puntaje * peso }

        return (sumaPonderada.toDouble() / (pesoTotal * 5)) * 100
    }

    fun obtenerCategoria(puntaje: Double): String {
        return when {
            puntaje >= 85.0 -> "Sobresaliente"
            puntaje >= 70.0 -> "Sólida"
            puntaje >= 50.0 -> "En desarrollo"
            else -> "Insuficiente"
        }
    }
}