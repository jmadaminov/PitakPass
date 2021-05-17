package com.novatec.epitak_passenger.remote.model

import android.os.Parcelable
import com.novatec.epitak_passenger.domain.model.CarInPost
import com.novatec.epitak_passenger.domain.model.ProfileDTO
import com.novatec.epitak_passenger.core.enums.EOfferStatus
import com.novatec.epitak_passenger.core.enums.EPostType
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferDTO(@SerializedName("id") val id: Long,
                    @SerializedName("postId") val postId: Long,
                    @SerializedName("postType") val postType: EPostType,
                    @SerializedName("senderId") val profileId: Long,
                    @SerializedName("status") val status: EOfferStatus,
                    @SerializedName("visible") val visible: Boolean,
                    @SerializedName("senderProfile") val profile: ProfileDTO,
                    @SerializedName("submitDate") val submitDate: String,
                    @SerializedName("message") val message: String?=null,
                    @SerializedName("car") val car: CarInPost?=null,
                    @SerializedName("price") val price: Int?=null) : Parcelable
