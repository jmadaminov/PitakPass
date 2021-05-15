package com.novatec.pitakpass.domain.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

/**
 * Representation for a [Filter] fetched from the API
 */




data class Filter(@SerializedName("airConditioner") var airConditioner: Boolean? = null,
                  @SerializedName("departureDate") var departureDate: String? = null,
                  @SerializedName("fromDistrictId") var fromDistrictId: Int? = null,
                  @SerializedName("fromRegionId") var fromRegionId: Int? = null,
                  @SerializedName("toDistrictId") var toDistrictId: Int? = null,
                  @SerializedName("toRegionId") var toRegionId: Int? = null,
                  @SerializedName("maxPrice") var maxPrice: Int? = null,
                  @SerializedName("minPrice") var minPrice: Int? = null,
                  @SerializedName("priceOrder") var priceOrder: String? = null,
                  @SerializedName("seat") var seat: Int? = null,
                  @SerializedName("timeFirstPart") var timeFirstPart: Boolean = true,
                  @SerializedName("timeFourthPart") var timeFourthPart: Boolean = true,
                  @SerializedName("timeSecondPart") var timeSecondPart: Boolean = true,
                  @SerializedName("timeThirdPart") var timeThirdPart: Boolean = true){

    companion object {
        const val MAX_PRICE = 500000
        const val MIN_PRICE = 10000
        const val STEP = 10000
    }

}