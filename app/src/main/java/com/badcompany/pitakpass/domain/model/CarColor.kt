package com.badcompany.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

data class CarColor(@SerializedName("id") val id: Long,
                    @SerializedName("hex") val hex: String,
                    @SerializedName("name") val name: String? = null)