package com.badcompany.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

data class AuthBody(@SerializedName("phoneNum") val phoneNum: String? = null,
                    @SerializedName("name")  val name: String? = null,
                    @SerializedName("surname")  val surname: String? = null,
                    @SerializedName("jwt")  val jwt: String? = null,
                    @SerializedName("role")  val role: String? = null)