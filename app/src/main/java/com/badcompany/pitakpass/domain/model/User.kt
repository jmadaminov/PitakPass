package com.badcompany.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("phoneNum") val phoneNum: String,
                @SerializedName("name") val name: String,
                @SerializedName("surname") val surname: String,
                @SerializedName("role") val role: String)