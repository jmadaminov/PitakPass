package com.badcompany.pitakpass.util

import com.google.gson.annotations.SerializedName

 data class ResponseParent(@SerializedName("code") val code: Int? = null,
                           @SerializedName("message") val message: String? = null,
                           @SerializedName("data") val data: Any? = null)