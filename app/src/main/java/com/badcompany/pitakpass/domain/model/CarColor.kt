package com.badcompany.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

data class CarColor(@SerializedName("id") val id: Long,
                    @SerializedName("hex") val hex: String,
                    @SerializedName("nameEn") val nameEn: String? = null,
                    @SerializedName("nameUz") val nameUz: String? = null,
                    @SerializedName("nameRu") val nameRu: String? = null)