package com.badcompany.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

data class PassengerOffer(@SerializedName("postId") val postId: Long,
                          @SerializedName("price") val price: Int? = null,
                          @SerializedName("message") val message: String? = null,
                          @SerializedName("seat") val seat: Int? = null,
                          @SerializedName("repliedPostId") val repliedPostId: Long? = null)