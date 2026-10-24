package com.example.desarrollo_mobile.di

import android.content.Context
import com.example.desarrollo_mobile.data.local.WimDatabase
import com.example.desarrollo_mobile.data.repository.WimRepository

interface AppContainer {
    val wimRepository: WimRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val database: WimDatabase by lazy {
        WimDatabase.getDatabase(context)
    }

    override val wimRepository: WimRepository by lazy {
        WimRepository(
            candidataDao = database.candidataDao(),
            criterioDao = database.criterioDao(),
            evaluacionDao = database.evaluacionDao(),
            historialDao = database.historialDao()
        )
    }
}