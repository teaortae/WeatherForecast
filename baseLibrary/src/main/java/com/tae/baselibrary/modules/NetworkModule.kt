package com.tae.baselibrary.modules

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.tae.baselibrary.api.NetworkConst
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object NetworkModule {
    private const val RESTFUL_TIMEOUT_MI_SEC = 60 * 1000
    val module = module {
        single { getOkHttpClient() }
        single { getRetrofit(get()) }
    }

    inline fun <reified T> getService(retrofit: Retrofit): T = retrofit.create(T::class.java)

    @Throws(Exception::class)
    private fun getOkHttpClient(): OkHttpClient {

        val httpClient = OkHttpClient.Builder().apply {
            connectTimeout(RESTFUL_TIMEOUT_MI_SEC.toLong(), TimeUnit.MILLISECONDS)
            readTimeout(RESTFUL_TIMEOUT_MI_SEC.toLong(), TimeUnit.MILLISECONDS)
            writeTimeout(RESTFUL_TIMEOUT_MI_SEC.toLong(), TimeUnit.MILLISECONDS)
            retryOnConnectionFailure(false)

            val interceptor =
                HttpLoggingInterceptor { message -> Log.d("network module : ", message) }

            interceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(interceptor)
            addNetworkInterceptor(StethoInterceptor())
            addInterceptor(HeaderSettingInterceptor())
        }



        return httpClient.build()
    }

    @Throws(Exception::class)
    private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(NetworkConst.HTTP_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapterFactory(CustomFieldTypeAdapterFactory())
                    .create()
            )
        )
        .client(okHttpClient)
        .build()


    private class CustomFieldTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {
            val adapter = gson.getDelegateAdapter(this, type)

            return object : TypeAdapter<T>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: T) {
                    adapter.write(out, value)
                }

                @Throws(IOException::class)
                override fun read(js: JsonReader): T {
                    return adapter.read(js)
                }
            }
        }
    }
}

open class HeaderSettingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        return chain.runCatching {
            val headers: Headers = request.getHeader()
            request = request
                .newBuilder()
                .headers(headers)
                .build()
            proceed(request)
        }.getOrNull() ?: chain.proceed(request)
    }

    @Throws(Exception::class)
    fun Request.getHeader(): Headers {
        return headers.newBuilder()
            .add(NetworkConst.ACCEPT, NetworkConst.applicationGJson)
            .add(NetworkConst.AUTH, NetworkConst.apiKey)
            .build()
    }
}
