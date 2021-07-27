package com.novatec.epitak_passenger.util

sealed class ResultWrapper<out V> {
    data class Success<out V>(val value: V) : ResultWrapper<V>()
    object InProgress : ResultWrapper<Nothing>()
}

sealed class ErrorWrapper : ResultWrapper<Nothing>() {
    data class ResponseError(val code: Int? = null, val message: String = "") : ErrorWrapper()
    data class SystemError(val err: Throwable) : ErrorWrapper()
}
