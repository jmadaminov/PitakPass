package com.novatec.epitak_passenger.remote

import android.util.Log
import com.novatec.epitak_passenger.BuildConfig.DEBUG
import com.novatec.epitak_passenger.util.UserPrefs
import okhttp3.Interceptor
import okhttp3.Response
import splitties.experimental.ExperimentalSplittiesApi

/**
 * Interceptor to add auth token to requests
 */
@ExperimentalSplittiesApi
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (UserPrefs.token.isBlank()) throw Exception()
        requestBuilder.addHeader("Authorization", "${UserPrefs.token}")
        if (DEBUG) Log.d("TOKEEEEN", UserPrefs.token)
        return chain.proceed(requestBuilder.build())
    }
}