package com.example.weatherforecast.ui.api

import com.tae.baselibrary.api.NetworkConst
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface WeatherService {
    @POST("forecast")
    suspend fun getWeatherForecast(
        @Query("q") q: String,
        @Query("appid") appId: String = NetworkConst.apiKey,
        @Query("units") units: String = "metric",
    ):Response<WeatherData>
}