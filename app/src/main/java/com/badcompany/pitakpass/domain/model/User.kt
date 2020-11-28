package com.badcompany.pitakpass.domain.model

import com.badcompany.pitakpass.util.Constants
import com.google.gson.annotations.SerializedName

data class User(@SerializedName("phoneNum") val phoneNum: String,
                @SerializedName("name") val name: String,
                @SerializedName("surname") val surname: String,
                @SerializedName("udId") val udId: String)