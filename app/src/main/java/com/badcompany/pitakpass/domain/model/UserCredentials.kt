package com.badcompany.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

data class UserCredentials(@SerializedName("phoneNum") var phoneNum: String? = null,
                           @SerializedName("password") var password: String? = null)

