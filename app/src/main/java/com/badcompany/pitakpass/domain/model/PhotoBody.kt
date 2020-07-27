package com.badcompany.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

data class PhotoBody (@SerializedName("id") val id: Long? = null,
                     @SerializedName("name") val name: String? = null,
                     @SerializedName("type") val type: String? = null,
                     @SerializedName("size") val size: Long? = null,
                     @SerializedName("link") val link: String? = null)