package com.badcompany.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

data class Image(@SerializedName("id") var id: Long? = null,
                 @SerializedName("link") var link: String? = null)