package com.example.weatherforecast.module

import com.example.weatherforecast.ui.api.WeatherService
import com.tae.baselibrary.modules.NetworkModule.getService
import org.koin.dsl.module

object NetworkService {
    val module = module {
        single { getService<WeatherService>(get()) }
    }
}