package com.example.weatherforecast.ui.api

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.weatherforecast.*
import com.example.weatherforecast.ext.EEE_dd_MMM
import com.example.weatherforecast.ext.YYYY_MM_dd_hhmmss
import com.example.weatherforecast.ext.dateFormatChange
import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt


data class WeatherData(
    @SerializedName("city")
    val city: City = City(),
    @SerializedName("cnt")
    val cnt: Int = 0, // 40
    @SerializedName("cod")
    val cod: String = "", // 200
    @SerializedName("list")
    val list: List<ForeCasts> = emptyList(),
    @SerializedName("message")
    val message: Int = 0// 0
)

data class City(
    @SerializedName("coord")
    val coord: Coord = Coord(),
    @SerializedName("country")
    val country: String = "", // GB
    @SerializedName("id")
    val id: Int = 0, // 2643743
    @SerializedName("name")
    val name: String = "", // London
    @SerializedName("population")
    val population: Int = 0, // 1000000
    @SerializedName("sunrise")
    val sunrise: Int = 0, // 1674028620
    @SerializedName("sunset")
    val sunset: Int = 0, // 1674059049
    @SerializedName("timezone")
    val timezone: Int = 0 // 0
)

data class ForeCasts(
    @SerializedName("clouds")
    val clouds: Clouds = Clouds(),
    @SerializedName("dt")
    val dt: Int = 0, // 1674010800
    @SerializedName("dt_txt")
    val dtTxt: String = "", // 2023-01-18 03:00:00
    @SerializedName("main")
    val main: Main = Main(),
//    @SerializedName("pop")
//    val pop: Int = 0, // 0
    @SerializedName("sys")
    val sys: Sys = Sys(),
    @SerializedName("visibility")
    val visibility: Int = 0, // 10000
    @SerializedName("weather")
    val weather: List<Weather> = emptyList(),
    @SerializedName("wind")
    val wind: Wind = Wind()
) {

    val dateTxt: String
        get() {
            return try {
                dtTxt.dateFormatChange(YYYY_MM_dd_hhmmss, EEE_dd_MMM)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }
    val weatherStr: String
        get() {
            return try {
                weather[0].main
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }
    val weatherIcon: Drawable?
        get() {
            return when (weather[0].icon) {

                //night
                WeatherIcon.IC_01D.toString(), WeatherIcon.IC_01N.toString() -> ContextCompat.getDrawable(
                    App.INSTANCE,
                    R.drawable.ic_day_sunny
                )
                WeatherIcon.IC_02D.toString(), WeatherIcon.IC_02N.toString() -> ContextCompat.getDrawable(
                    App.INSTANCE,
                    R.drawable.ic_day_cloudy
                )
                WeatherIcon.IC_03D.toString(), WeatherIcon.IC_03N.toString() -> ContextCompat.getDrawable(
                    App.INSTANCE,
                    R.drawable.ic_cloud
                )
                WeatherIcon.IC_04D.toString(), WeatherIcon.IC_04N.toString() -> ContextCompat.getDrawable(
                    App.INSTANCE,
                    R.drawable.ic_cloudy
                )
                WeatherIcon.IC_09D.toString(), WeatherIcon.IC_09N.toString() -> ContextCompat.getDrawable(
                    App.INSTANCE,
                    R.drawable.ic_rain
                )
                WeatherIcon.IC_10D.toString(), WeatherIcon.IC_10N.toString() -> ContextCompat.getDrawable(
                    App.INSTANCE,
                    R.drawable.ic_day_rain
                )
                WeatherIcon.IC_11D.toString(), WeatherIcon.IC_11N.toString() -> ContextCompat.getDrawable(
                    App.INSTANCE,
                    R.drawable.ic_storm_showers
                )
                WeatherIcon.IC_13D.toString(), WeatherIcon.IC_13N.toString() -> ContextCompat.getDrawable(
                    App.INSTANCE,
                    R.drawable.ic_snow
                )
                WeatherIcon.IC_50D.toString(), WeatherIcon.IC_50N.toString() -> ContextCompat.getDrawable(
                    App.INSTANCE,
                    R.drawable.ic_fog
                )
                else -> ContextCompat.getDrawable(App.INSTANCE, R.drawable.ic_day_sunny)
            }
        }
}

data class Coord(
    @SerializedName("lat")
    val lat: Double = .0, // 51.5085
    @SerializedName("lon")
    val lon: Double = .0 // -0.1257
)

data class Clouds(
    @SerializedName("all")
    val all: Int = 0 // 7
)

data class Main(
    @SerializedName("feels_like")
    val feelsLike: Double = .0, // 267.41
    @SerializedName("grnd_level")
    val grndLevel: Int = 0, // 991
    @SerializedName("humidity")
    val humidity: Int = 0, // 88
    @SerializedName("pressure")
    val pressure: Int = 0, // 992
    @SerializedName("sea_level")
    val seaLevel: Int = 0, // 992
    @SerializedName("temp")
    val temp: Double = .0, // 271.43
    @SerializedName("temp_kf")
    val tempKf: Double = .0, // -1.93
    @SerializedName("temp_max")
    val tempMax: Double = .0, // 273.36
    @SerializedName("temp_min")
    val tempMin: Double = .0 // 271.43
) {
    val minMaxTemp: String
        get() {
            return "Max : ${tempMax.roundToInt()}°C   Min : ${tempMin.roundToInt()}°C"
        }
}

data class Sys(
    @SerializedName("pod")
    val pod: String = "" // n
)

data class Weather(
    @SerializedName("description")
    val description: String = "", // clear sky
    @SerializedName("icon")
    val icon: String = "", // 01n
    @SerializedName("id")
    val id: Int = 0, // 800
    @SerializedName("main")
    val main: String = "" // Clear
) {

}

data class Wind(
    @SerializedName("deg")
    val deg: Int = 0, // 253
    @SerializedName("gust")
    val gust: Double = .0, // 9.56
    @SerializedName("speed")
    val speed: Double = .0 // 3.19
)

enum class WeatherIcon(private val weatherType: String) {
    IC_01D("01d"),
    IC_02D("02d"),
    IC_03D("03d"),
    IC_04D("04d"),
    IC_09D("09d"),
    IC_10D("10d"),
    IC_11D("11d"),
    IC_13D("13d"),
    IC_50D("50d"),
    IC_01N("01n"),
    IC_02N("02n"),
    IC_03N("03n"),
    IC_04N("04n"),
    IC_09N("09n"),
    IC_10N("10n"),
    IC_11N("11n"),
    IC_13N("13n"),
    IC_50N("50n");

    override fun toString(): String {
        return weatherType
    }
}