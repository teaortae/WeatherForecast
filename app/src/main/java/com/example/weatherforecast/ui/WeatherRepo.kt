package com.example.weatherforecast.ui

import com.example.weatherforecast.ui.api.WeatherData
import com.example.weatherforecast.ui.api.WeatherService
import com.tae.baselibrary.api.ApiResult
import com.tae.baselibrary.api.serverResult
import com.tae.baselibrary.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeatherRepo(private val api: WeatherService) : BaseRepository() {

    fun getWeatherForecaster(city: String): Flow<ApiResult<WeatherData>?> =
        flow<ApiResult<WeatherData>?> {
            emit(serverResult(api.getWeatherForecast(city))) //emit server result
        }.flowOn(Dispatchers.IO)
}