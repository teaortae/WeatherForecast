package com.example.weatherforecast.ui

import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.*
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.example.weatherforecast.ext.progressDialog
import com.tae.baselibrary.BaseActivity
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, WeatherVMImpl>(R.layout.activity_main) {

    override val viewModel: WeatherVMImpl by viewModel()

    override fun initData() {
        App.INSTANCE.progressDialog = progressDialog()
        App.INSTANCE.progressDialog?.show()
        viewModel.getWeather()
    }

    override fun initView() {


        binding.rvWeather.layoutManager = LinearLayoutManager(this)
    }

    override fun eventObservers() {
        combine(
            viewModel.seoulWeatherTask.asFlow(),
            viewModel.londonWeatherTask.asFlow(),
            viewModel.chicagoWeatherTask.asFlow()
        ) { seoulWeather, londonWeather, chicagoWeather ->
            //draw recycler with epoxy
            binding.rvWeather.withModels {
                //seoul
                weatherTitle {
                    id(seoulWeather.city.id)
                    cityName(seoulWeather.city.name)
                }
                seoulWeather.list
                    .distinctBy { it.dateTxt }
                    .map { forecast ->
                        weather {
                            id(forecast.dt)
                            data(forecast)
                        }
                    }
                //london
                weatherTitle {
                    id(londonWeather.city.id)
                    cityName(londonWeather.city.name)
                }
                londonWeather.list
                    .distinctBy { it.dateTxt }
                    .map { forecast ->
                        weather {
                            id(forecast.dt)
                            data(forecast)
                        }
                    }
                //chicago
                weatherTitle {
                    id(chicagoWeather.city.id)
                    cityName(chicagoWeather.city.name)
                }
                chicagoWeather.list
                    .distinctBy { it.dateTxt }
                    .map { forecast ->
                        weather {
                            id(forecast.dt)
                            data(forecast)
                        }
                    }

            }
            App.INSTANCE.progressDialog?.dismiss()
        }
            .launchIn(lifecycleScope)
    }
}

