package com.badcompany.pitakpass.remote.model

import com.badcompany.pitakpass.util.Constants
import com.google.gson.annotations.SerializedName

/**
 * Representation for a [LoginRequest] fetched from the API
 */
data class LoginRequest(@SerializedName("phoneNum") val phoneNum: String,
                        @SerializedName("userType") val userType: String = Constants.ROLE_PASSENGER)