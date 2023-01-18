package com.tae.baselibrary.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.tae.baselibrary.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error<out T : Any>(val error: T) : ApiResult<T>()
}

inline fun <reified T : Any> serverResult(result: Response<T>?): ApiResult<T> {
    return result?.let {
        when {
            result.isSuccessful -> {
                val response = result.body()
                ApiResult.Success<T>(
                    response ?: Gson().fromJson(
                        result.errorBody()?.string(),
                        T::class.java
                    )
                )
            }
            else -> {
                when (it.code()) {
                    401, 403, 404, 405,
                    500, 502, 301, 302 -> ApiResult.Error(
                        Gson().fromJson(
                            result.errorBody()?.string(), T::class.java
                        )
                    )
                    else -> ApiResult.Error(
                        Gson().fromJson(
                            result.errorBody()?.string(),
                            T::class.java
                        )
                    )
                }
            }
        }
    } ?: ApiResult.Error(Gson().fromJson(result?.errorBody()?.string(), T::class.java))
}

fun getBody(key: String, value: Any): MultipartBody.Part {
    return MultipartBody.Part.createFormData(key, value.toString())
}

fun getImageBody(key: String, file: File?): MultipartBody.Part? {
    return file?.asRequestBody("image/*".toMediaType())?.let {
        MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = it
        )
    }
}