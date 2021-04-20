package com.novatec.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

data class CarDetails(@SerializedName("id") var id: Long? = null,
                      @SerializedName("carModel") var carModel: IdName? = null,
                      @SerializedName("image") var image: Image? = null,
                      @SerializedName("fuelType") var fuelType: String? = null,
                      @SerializedName("carColor") var carColor: CarColor? = null,
                      @SerializedName("carNumber") var carNumber: String? = null,
                      @SerializedName("carYear") var carYear: Int? = null,
                      @SerializedName("airConditioner") var airConditioner: Boolean? = null,
                      @SerializedName("def") var def: Boolean? = null,
                      @SerializedName("imageList") var imageList: List<Image>? = null)



