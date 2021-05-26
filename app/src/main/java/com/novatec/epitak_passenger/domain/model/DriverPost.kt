package com.novatec.epitak_passenger.domain.model

import com.google.gson.annotations.SerializedName
import com.novatec.epitak_passenger.core.enums.EPostType

data class DriverPost(
    @SerializedName("id") val id: Long,
    @SerializedName("from") val from: Place,
    @SerializedName("to") val to: Place,
    @SerializedName("price") val price: Int,
    @SerializedName("departureDate") val departureDate: String,
    @SerializedName("finishedDate") val finishedDate: String? = null,
    @SerializedName("timeFirstPart") val timeFirstPart: Boolean,
    @SerializedName("timeSecondPart") val timeSecondPart: Boolean,
    @SerializedName("timeThirdPart") val timeThirdPart: Boolean,
    @SerializedName("timeFourthPart") val timeFourthPart: Boolean,
    @SerializedName("carId") val carId: Long? = null,
    @SerializedName("car") val car: CarInPost? = null,
    @SerializedName("profile") val profile: ProfileDTO? = null,
    @SerializedName("remark") val remark: String? = null,
    @SerializedName("seat") val seat: Int,
    @SerializedName("availableSeats") val availableSeats: Int,
    @SerializedName("myOffer") val myLastOffer: UserOffer? = null,
    @SerializedName("pkg") val pkg: Boolean = false,
    @SerializedName("passengerList") val passengerList: List<Passenger>? = null,
    @SerializedName("postType") val postType: EPostType = EPostType.DRIVER_SM
)


data class UserOffer(
    @SerializedName("id") val id: Long,
    @SerializedName("postId") val postId: Long,
    @SerializedName("repliedPostId") val repliedPostId: Long,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("submitDate") val submitDate: String,
    @SerializedName("priceInt") val priceInt: Int,
    @SerializedName("seat") val seat: Int
)

