package com.novatec.pitakpass.viewobjects

import android.os.Parcelable
import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.domain.model.Place
import com.novatec.pitakpass.core.enums.EPostStatus
import com.novatec.pitakpass.core.enums.EPostType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PassengerPostViewObj(val from: PlaceViewObj,
                                val to: PlaceViewObj,
                                val price: Int,
                                val departureDate: String,
                                val timeFirstPart: Boolean,
                                val timeSecondPart: Boolean,
                                val timeThirdPart: Boolean,
                                val timeFourthPart: Boolean,
                                val remark: String? = null,
                                val seat: Int,
                                val postStatus: EPostStatus,
                                val postType: EPostType = EPostType.PASSENGER_SM) : Parcelable {

    companion object {

        fun fromPassengerPost(post: PassengerPost): PassengerPostViewObj {
            return PassengerPostViewObj(PlaceViewObj.fromPlace(post.from),
                                        PlaceViewObj.fromPlace(post.to),
                                        post.price,
                                        post.departureDate,
                                        post.timeFirstPart,
                                        post.timeSecondPart,
                                        post.timeThirdPart,
                                        post.timeFourthPart,
                                        post.remark,
                                        post.seat,
                                        post.postStatus!!,
                                        post.postType)
        }

    }

}

@Parcelize
data class PlaceViewObj(val districtId: Int? = null,
                        val regionId: Int? = null,
                        val lat: Double? = null,
                        val lon: Double? = null,
                        val regionName: String? = null,
                        val name: String? = null) : Parcelable {

    companion object {


        fun fromPlace(place: Place): PlaceViewObj {
            return PlaceViewObj(place.districtId,
                                place.regionId,
                                place.lat,
                                place.lon,
                                place.regionName,
                                place.name)

        }

    }

}