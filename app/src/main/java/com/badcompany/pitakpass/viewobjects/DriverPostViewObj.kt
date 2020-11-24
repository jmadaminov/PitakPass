package com.badcompany.pitakpass.viewobjects

import android.os.Parcelable
import com.badcompany.pitakpass.domain.model.CarModel
import com.badcompany.pitakpass.domain.model.Image
import com.badcompany.pitakpass.ui.EPostType
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DriverPostViewObj( val id: Long,
                              val from: PlaceViewObj,
                              val to: PlaceViewObj,
                              val price: Int,
                              val departureDate: String,
                              val finishedDate: String? = null,
                              val timeFirstPart: Boolean,
                              val timeSecondPart: Boolean,
                              val timeThirdPart: Boolean,
                              val timeFourthPart: Boolean,
                              val carId: Long? = null,
                              val car: CarInPostViewObj? = null,
                              val remark: String,
                              val seat: Int,
                              val postType: EPostType):Parcelable




/**
 * Representation for a [CarInPost] fetched from the API
 */
@Parcelize
data class CarInPostViewObj( var id: Long? = null,
                      var modelId: Long? = null,
                      var image: ImageViewObj? = null,
                      var carModel: CarModelViewObj?=null,
                      var fuelType: String? = null,
                      var colorId: Long? = null,
                      var carNumber: String? = null,
                      var carYear: Int? = null,
                      var airConditioner: Boolean? = null):Parcelable


@Parcelize
data class CarModelViewObj ( val id: Long,
                      val name: String):Parcelable
