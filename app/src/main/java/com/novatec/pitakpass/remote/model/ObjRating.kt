package com.novatec.pitakpass.remote.model

import com.google.gson.annotations.SerializedName

data class ObjRating(@SerializedName("id") val id: Long? = null,
                     @SerializedName("driverId") val driverId: Long? = null,
                     @SerializedName("submitDate") val submitDate: String? = null,
                     @SerializedName("rating") var rating: Float? = null)
