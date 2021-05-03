package com.novatec.pitakpass.domain.model

import com.novatec.pitakpass.util.Constants
import com.google.gson.annotations.SerializedName

data class User(@SerializedName("phoneNum") val phoneNum: String,
                @SerializedName("name") val name: String,
                @SerializedName("surname") val surname: String,
                @SerializedName("udId") val udId: String,
                @SerializedName("userType") val userType: String)