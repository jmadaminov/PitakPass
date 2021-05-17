package com.novatec.epitak_passenger.domain.model
import com.novatec.epitak_passenger.core.enums.EPostStatus
import com.novatec.epitak_passenger.core.enums.EPostType
import com.google.gson.annotations.SerializedName
import com.novatec.epitak_passenger.remote.model.OfferDTO

/**
 * Representation for a [PassengerPost] fetched from the API
 */
data class PassengerPost(@SerializedName("id") val id: Long?=null,
                         @SerializedName("from") val from: Place,
                         @SerializedName("to") val to: Place,
                         @SerializedName("price") val price: Int,
                         @SerializedName("departureDate") val departureDate: String,
                         @SerializedName("createdDate") val createdDate: String?=null,
                         @SerializedName("updatedDate") val updatedDate: String?=null,
                         @SerializedName("finishedDate") val finishedDate: String?=null,
                         @SerializedName("timeFirstPart") val timeFirstPart: Boolean,
                         @SerializedName("timeSecondPart") val timeSecondPart: Boolean,
                         @SerializedName("timeThirdPart") val timeThirdPart: Boolean,
                         @SerializedName("timeFourthPart") val timeFourthPart: Boolean,
                         @SerializedName("airConditioner") val airConditioner: Boolean?=null,
                         @SerializedName("driverPost") val driverPost: DriverPost?=null,
                         @SerializedName("remark") val remark: String?=null,
                         @SerializedName("postStatus") val postStatus: EPostStatus,
                         @SerializedName("seat") val seat: Int,
                         @SerializedName("offerCount") val offerCount: Int,
                         @SerializedName("offer") val offer: OfferDTO?=null,
                         @SerializedName("postType") val postType: EPostType = EPostType.PASSENGER_SM)