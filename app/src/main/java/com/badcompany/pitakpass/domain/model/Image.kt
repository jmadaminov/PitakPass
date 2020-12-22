package com.badcompany.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

data class Image(@SerializedName("link") var link: String? = null,
                 @SerializedName("id") var id: Long? = null)