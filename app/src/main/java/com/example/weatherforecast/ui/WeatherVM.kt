package com.example.weatherforecast.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.ui.api.WeatherData
import com.tae.baselibrary.api.ApiResult
import com.tae.baselibrary.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface WeatherVM {
    fun getWeather()
}

class WeatherVMImpl(private val repo: WeatherRepo) : BaseViewModel(), WeatherVM {

    private val _seoulWeather = MutableLiveData<WeatherData>()
    val seoulWeatherTask: LiveData<WeatherData> = _seoulWeather

    private val _londonWeather = MutableLiveData<WeatherData>()
    val londonWeatherTask: LiveData<WeatherData> = _londonWeather

    private val _chicagoWeather = MutableLiveData<WeatherData>()
    val chicagoWeatherTask: LiveData<WeatherData> = _chicagoWeather

    override fun getWeather() {
        repo.getWeatherForecaster("Seoul")
            .onEach {
                when (it) {
                    is ApiResult.Success -> _seoulWeather.value = it.data
                    is ApiResult.Error -> _seoulWeather.value = it.error
                    null -> _seoulWeather.value = WeatherData()
                }
            }
            .launchIn(viewModelScope)
        repo.getWeatherForecaster("London")
            .onEach {
                when (it) {
                    is ApiResult.Success -> _londonWeather.value = it.data
                    is ApiResult.Error -> _londonWeather.value = it.error
                    null -> _londonWeather.value = WeatherData()
                }
            }
            .launchIn(viewModelScope)
        repo.getWeatherForecaster("Chicago")
            .onEach {
                when (it) {
                    is ApiResult.Success -> _chicagoWeather.value = it.data
                    is ApiResult.Error -> _chicagoWeather.value = it.error
                    null -> _chicagoWeather.value = WeatherData()
                }
            }
            .launchIn(viewModelScope)
    }
}