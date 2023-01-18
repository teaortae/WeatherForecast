package com.tae.baselibrary.util

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import org.json.JSONObject

/**
 * **Log 와 같은 기능을 지원하는 클래스**
 *
 *
 *
 * 테스트시에는 BuildConfig.DEBUG 가 true 상태여서 logcat 으로 로그를 분석할 수 있고, apk 생성하여 배포시에는
 * 자동으로 false 로 변경되어 로그가 출력되지 않는다.
 * Logger library 적극 활용
 *
 *
 * @author https://github.com/teaortae
 */
object Log {
    /**
     * Log Level Error
     */
    fun e(message: String) {
        Logger.e(message)
    }

    /**
     * Log Level Warning
     */
    fun w(message: String) {
        Logger.w(message)
    }

    /**
     * Log Level Information
     */
    fun i(message: String) {
        Logger.i(message)
    }

    /**
     * Log Level Debug
     */
    fun d(message: String) {
        Logger.d(message)
    }

    fun dd(message: String, param: String) {
        Logger.d("$message: $param")
    }

    /**
     * Log Level Verbose
     */
    fun v(message: String) {
        Logger.v(message)
    }

    /**
     * Log Level Error
     */
    fun e(TAG: String, message: String?) {
        Logger.e(TAG, message)
    }

    /**
     * Log Level Error
     */
    fun e(message: String, tr: Throwable?) {
        Logger.e(message, tr)
    }

    /**
     * Log Level Warning
     */
    fun w(message: String, tr: Throwable?) {
        Logger.w(message, tr)
    }

    /**
     * Log Level Information
     */
    fun i(message: String, tr: Throwable?) {
        Logger.i(message, tr)
    }

    /**
     * Log Level Debug
     */
    fun d(message: String, tr: Throwable?) {
        Logger.d(message, tr)
    }

    /**
     * Log Level Verbose
     */
    fun v(message: String, tr: Throwable?) {
        Logger.v(message, tr)
    }

    fun logW(tag: String, msg: String) {
        Logger.t(tag)
        Logger.w(msg)
    }

    fun json(msg: Any?) {
        try {
            val json: String = Gson().toJson(msg)
            Logger.json(json)
            JSONObject(json)
        } catch (e: Exception) {
            Logger.d(msg)
        }
    }
}