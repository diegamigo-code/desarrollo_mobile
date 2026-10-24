package com.example.desarrollo_mobile.di

import android.app.Application

class WimApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}