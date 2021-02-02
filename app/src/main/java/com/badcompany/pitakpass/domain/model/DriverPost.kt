package com.badcompany.pitakpass.domain.model

import com.badcompany.pitakpass.ui.EPostType
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResponseParent
import com.google.gson.annotations.SerializedName

data class DriverPost(@SerializedName("id") val id: Long,
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
                      @SerializedName("remark") val remark: String?=null,
                      @SerializedName("seat") val seat: Int,
                      @SerializedName("availableSeat") val availableSeats: Int,
                      @SerializedName("postType") val postType: EPostType = EPostType.DRIVER_SM)

