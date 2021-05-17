package com.novatec.epitak_passenger.remote.model

import com.novatec.epitak_passenger.util.Constants
import com.google.gson.annotations.SerializedName

/**
 * Representation for a [LoginRequest] fetched from the API
 */
data class LoginRequest(@SerializedName("phoneNum") val phoneNum: String,
                        @SerializedName("userType") val userType: String = Constants.ROLE_PASSENGER)