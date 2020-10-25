package com.badcompany.pitakpass.util

import org.json.JSONObject
import retrofit2.HttpException

sealed class ResponseWrapper<out V>
data class ResponseError(val message: String? = null, val code: Int? = null) :
    ResponseWrapper<Nothing>()

data class ResponseSuccess<out V>(val value: V) : ResponseWrapper<V>()


suspend fun <T> getFormattedResponse(action: suspend () -> RespFormatter<T>): ResponseWrapper<T> {
    return try {
        val resp = action()
        when {
            resp.data != null -> ResponseSuccess(resp.data)
            resp.code == 1 -> ResponseError("DATA NULL")
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
