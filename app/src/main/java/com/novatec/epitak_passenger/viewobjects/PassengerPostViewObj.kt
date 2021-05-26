package com.novatec.epitak_passenger.viewobjects

import android.os.Parcelable
import com.novatec.epitak_passenger.core.enums.EPostStatus
import com.novatec.epitak_passenger.core.enums.EPostType
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.domain.model.Place
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PassengerPostViewObj(
    val id: Long? = null,
    val from: PlaceViewObj,
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
    val postType: EPostType = EPostType.PASSENGER_SM
) : Parcelable {

    companion object {

        fun fromPassengerPost(post: PassengerPost): PassengerPostViewObj {
            return PassengerPostViewObj(
                post.id,
                PlaceViewObj.fromPlace(post.from),
                PlaceViewObj.fromPlace(post.to),
                post.price,
                post.departureDate,
                post.timeFirstPart,
                post.timeSecondPart,
                post.timeThirdPart,
                post.timeFourthPart,
                post.remark,
                post.seat,
                post.postStatus,
                post.postType
            )
        }

    }

}

@Parcelize
data class PlaceViewObj(
    val districtId: Int? = null,
    val regionId: Int? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val regionName: String? = null,
    val districtName: String? = null,
    val name: String? = null
) : Parcelable {

    fun toPlace(): Place {
        return Place(
            districtId,
            regionId,
            lat,
            lon,
            regionName,
            districtName,
            name
        )
    }

    companion object {


        fun fromPlace(place: Place): PlaceViewObj {
            return PlaceViewObj(
                place.districtId,
                place.regionId,
                place.lat,
                place.lon,
                place.regionName,
                place.districtName,
                place.name
            )

        }

    }

}