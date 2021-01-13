package com.badcompany.pitakpass.remote.model

import android.os.Parcelable
import com.badcompany.pitakpass.domain.model.ProfileDTO
import com.badcompany.pitakpass.ui.EOfferStatus
import com.badcompany.pitakpass.ui.EPostType
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferDTO(@SerializedName("id") val id: Long,
                    @SerializedName("postId") val postId: Long,
                    @SerializedName("postType") val postType: EPostType,
                    @SerializedName("profileId") val profileId: Long,
                    @SerializedName("status") val status: EOfferStatus,
                    @SerializedName("visible") val visible: Boolean,
                    @SerializedName("profile") val profileDTO: ProfileDTO,
                    @SerializedName("submitDate") val submitDate: String,
                    @SerializedName("message") val message: String?=null,
                    @SerializedName("price") val price: Int?=null) : Parcelable
