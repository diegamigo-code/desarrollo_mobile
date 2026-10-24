package com.example.desarrollo_mobile.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.desarrollo_mobile.di.WimApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SesionViewModel(
                repository = wimApplication().container.wimRepository
            )
        }
        initializer {
            CandidatasViewModel(
                repository = wimApplication().container.wimRepository
            )
        }
        initializer {
            EvaluacionViewModel(
                repository = wimApplication().container.wimRepository
            )
        }
    }
}

fun CreationExtras.wimApplication(): WimApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WimApplication)