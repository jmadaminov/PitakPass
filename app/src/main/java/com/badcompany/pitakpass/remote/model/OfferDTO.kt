package com.badcompany.pitakpass.remote.model

import com.badcompany.pitakpass.ui.EPostType
import com.google.gson.annotations.SerializedName

data class OfferDTO(@SerializedName("id") val id: Long,
                    @SerializedName("postId") val postId: Long,
                    @SerializedName("postType") val postType: EPostType,
                    @SerializedName("profileId") val profileId: Long,
                    @SerializedName("status") val status: EPostType,
                    @SerializedName("visible") val visible: Boolean,
                    @SerializedName("submitDate") val submitDate: String,
                    @SerializedName("message") val message: String)
