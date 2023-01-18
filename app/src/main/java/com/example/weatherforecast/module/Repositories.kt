package com.example.weatherforecast.module

import com.example.weatherforecast.ui.WeatherRepo
import org.koin.dsl.module

object Repositories {
    val module = module {
        single { WeatherRepo(get()) }
    }
}