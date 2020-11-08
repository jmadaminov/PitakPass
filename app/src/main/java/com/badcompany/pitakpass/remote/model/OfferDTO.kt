package com.badcompany.pitakpass.remote.model

import com.google.gson.annotations.SerializedName

data class OfferDTO(@SerializedName("postId") val postId:Long,
                    @SerializedName("message") val message:String,)
