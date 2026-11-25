package com.example.desarrollo_mobile.di

import android.content.Context
import com.example.desarrollo_mobile.BuildConfig
import com.example.desarrollo_mobile.data.repository.RepositorioApp
import com.example.desarrollo_mobile.data.remote.RepositorioSpring
import com.example.desarrollo_mobile.data.remote.ServidorApi
import com.example.desarrollo_mobile.data.local.WimDatabase
import com.example.desarrollo_mobile.data.repository.WimRepository

interface AppContainer {
    val wimRepository: RepositorioApp
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val database: WimDatabase by lazy {
        WimDatabase.getDatabase(context)
    }

    override val wimRepository: RepositorioApp by lazy {
        if (BuildConfig.WIM_LOCAL_DEMO) WimRepository(
            candidataDao = database.candidataDao(),
            criterioDao = database.criterioDao(),
            evaluacionDao = database.evaluacionDao(),
            historialDao = database.historialDao()
        ) else RepositorioSpring(
            BuildConfig.WIM_API_URL,
            ServidorApi(BuildConfig.WIM_CONFIG_URL, BuildConfig.WIM_API_URL)
        )
    }
}