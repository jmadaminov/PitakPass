package com.novatec.pitakpass.domain.model

import android.os.Parcelable
import com.novatec.pitakpass.core.enums.EFuelType
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Representation for a [CarInPost] fetched from the API
 */
@Parcelize
data class CarInPost(@SerializedName("id") var id: Long? = null,
                     @SerializedName("modelId") var modelId: Long? = null,
                     @SerializedName("image") var image: Image? = null,
                     @SerializedName("carModel") var carModel: CarModel? = null,
                     @SerializedName("carColor") var carColor: CarColor? = null,
                     @SerializedName("fuelType") var fuelType: EFuelType? = null,
                     @SerializedName("colorId") var colorId: Long? = null,
                     @SerializedName("carNumber") var carNumber: String? = null,
                     @SerializedName("carYear") var carYear: Int? = null,
                     @SerializedName("airConditioner") var airConditioner: Boolean? = null) :
    Parcelable
