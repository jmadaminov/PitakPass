package com.badcompany.pitakpass.remote

import android.util.Log
import com.badcompany.pitakpass.BuildConfig.DEBUG
import com.badcompany.pitakpass.util.AppPrefs
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
        if (AppPrefs.token.isBlank()) throw Exception()
        requestBuilder.addHeader("Authorization", "${AppPrefs.token}")
        if (DEBUG) Log.d("TOKEEEEN", AppPrefs.token)
        return chain.proceed(requestBuilder.build())
    }
}