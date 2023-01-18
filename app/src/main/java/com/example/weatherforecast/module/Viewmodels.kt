package com.example.weatherforecast.module

import com.example.weatherforecast.ui.WeatherVMImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModels {
    val module = module {
        viewModel { WeatherVMImpl(get()) }
    }
}