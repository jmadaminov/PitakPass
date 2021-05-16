package com.novatec.pitakpass.util

import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import retrofit2.HttpException

sealed class ResponseWrapper<out V>
data class ResponseError(val message: String? = null, val code: Int? = null) :
    ResponseWrapper<Nothing>()

data class ResponseSuccess<out V>(val value: V) : ResponseWrapper<V>()



suspend fun <T> getApiResult(action: suspend () -> T): ResponseWrapper<T> {
    return try {
        ResponseSuccess(action())
    } catch (e: HttpException) {
        val error = e.response()?.errorBody()?.string()
        try {
            ResponseError(if (!error.isNullOrBlank()) {
                JSONObject(error)["message"].toString()
            } else e.message(), e.code())
        } catch (ex: java.lang.Exception) {
            ResponseError(e.message(), e.code())
        }
    } catch (e: Exception) {
        ResponseError(message = e.localizedMessage)
    }
}

suspend fun <T> getApiResult(liveData: MutableLiveData<ResponseWrapper<T>>, action: suspend () -> T) {
    try {
        val result = action()
        liveData.postValue(ResponseSuccess(result))
    } catch (e: HttpException) {
        val error = e.response()?.errorBody()?.string()
        try {
            liveData.postValue(ResponseError(if (!error.isNullOrBlank()) {
                JSONObject(error)["message"].toString()
            } else e.message(), e.code()))
        } catch (ex: java.lang.Exception) {
            liveData.postValue(ResponseError(e.message(), e.code()))
        }
    } catch (e: Exception) {
        liveData.postValue(
            ResponseError(message = e.localizedMessage))
    }
}


suspend fun <T> getFormattedResponse(action: suspend () -> RespFormatter<T>): ResponseWrapper<T> {
    return try {
        val resp = action()
        when {
            resp.code == 1 && resp.data != null -> ResponseSuccess(resp.data)
            else -> ResponseError(resp.message, resp.code)
        }
    } catch (e: HttpException) {
        val error = e.response()?.errorBody()?.string()
        try {
            ResponseError(if (!error.isNullOrBlank()) {
                JSONObject(error)["message"].toString()
            } else e.message(), e.code())
        } catch (ex: java.lang.Exception) {
            ResponseError(e.message(), e.code())
        }
    } catch (e: Exception) {
        ResponseError(message = e.localizedMessage)
    }
}


suspend fun <T> getFormattedResponseNullable(action: suspend () -> RespFormatter<T>): ResponseWrapper<T?> {
    return try {
        val resp = action()
        when {
            resp.code == 1 && resp.data != null -> ResponseSuccess(resp.data)
            resp.code == 1 && resp.data == null -> ResponseSuccess(resp.data)
            else -> ResponseError(resp.message, resp.code)
        }
    } catch (e: HttpException) {
        ResponseError(
            JSONObject(e.response()!!.errorBody()!!.string())["message"].toString(),
            e.code()
        )
    } catch (e: Exception) {
        ResponseError(message = e.localizedMessage)
    }
}

