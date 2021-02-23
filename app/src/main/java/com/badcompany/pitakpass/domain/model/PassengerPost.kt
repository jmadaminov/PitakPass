package com.badcompany.pitakpass.domain.model
import com.badcompany.pitakpass.core.enums.EPostStatus
import com.badcompany.pitakpass.core.enums.EPostType
import com.google.gson.annotations.SerializedName

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
                         @SerializedName("postType") val postType: EPostType = EPostType.PASSENGER_SM)