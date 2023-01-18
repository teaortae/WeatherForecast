package com.example.weatherforecast

import android.app.Application
import android.app.Dialog
import com.example.weatherforecast.module.NetworkService
import com.example.weatherforecast.module.Repositories
import com.example.weatherforecast.module.ViewModels
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tae.baselibrary.BuildConfig
import com.tae.baselibrary.api.NetworkConst
import com.tae.baselibrary.modules.BaseRepositoryModule
import com.tae.baselibrary.modules.BaseViewModelModule
import com.tae.baselibrary.modules.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    init {
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: App
    }

    var progressDialog: Dialog? = null

    override fun onCreate() {
        super.onCreate()
        //debug util
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    NetworkModule.module,
                    BaseRepositoryModule.module,
                    BaseViewModelModule.module,
                    ViewModels.module,
                    Repositories.module,
                    NetworkService.module,
                )
            )
        }

        //        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
        //                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
        //                .methodCount(2)         // (Optional) How many method line to show. Default 2
        //                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
        //                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
        //                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
        //                .build();

        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })


        NetworkConst.HTTP_URL = "https://api.openweathermap.org/data/2.5/"
    }
}

