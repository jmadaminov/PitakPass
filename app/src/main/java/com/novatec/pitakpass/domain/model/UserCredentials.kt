package com.novatec.pitakpass.domain.model

import com.novatec.pitakpass.util.Constants
import com.google.gson.annotations.SerializedName

data class UserCredentials(@SerializedName("phoneNum") var phoneNum: String? = null,
                           @SerializedName("password") var password: String? = null,
                           @SerializedName("udId") var udId: String? = null,
                           @SerializedName("userType") var userType: String=Constants.ROLE_PASSENGER)

