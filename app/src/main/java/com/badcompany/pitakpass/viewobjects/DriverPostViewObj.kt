package com.badcompany.pitakpass.viewobjects

import android.os.Parcelable
import com.badcompany.pitakpass.core.enums.EFuelType
import com.badcompany.pitakpass.core.enums.EPostType
import com.badcompany.pitakpass.domain.model.DriverPost
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DriverPostViewObj(val id: Long,
                             val from: PlaceViewObj,
                             val to: PlaceViewObj,
                             val price: Int,
                             val departureDate: String,
                             val finishedDate: String? = null,
                             val timeFirstPart: Boolean,
                             val timeSecondPart: Boolean,
                             val timeThirdPart: Boolean,
                             val timeFourthPart: Boolean,
                             val profile: ProfileViewObj? = null,
                             val carId: Long? = null,
                             val car: CarInPostViewObj? = null,
                             val remark: String? = null,
                             val seat: Int,
                             val passengerList: List<PassengerViewObj>? = null,
                             val postType: EPostType) : Parcelable {

    companion object {
        fun mapFromDriverPostModel(model: DriverPost): DriverPostViewObj {
            val profile = ProfileViewObj(model.profile?.phoneNum,
                                         model.profile?.name,
                                         model.profile?.surname,
                                         model.profile?.id,
                                         ImageViewObj(
                                             model.profile?.image?.id,
                                             model.profile?.image?.link,
                                         ))
            val passengerList = arrayListOf<PassengerViewObj>()
            model.passengerList?.forEach {
                passengerList.add(PassengerViewObj(it.id,
                                                   ProfileViewObj(it.profile?.phoneNum,
                                                                  it.profile?.name,
                                                                  it.profile?.surname,
                                                                  it.profile?.id,
                                                                  ImageViewObj(it.profile?.image?.id,
                                                                               it.profile?.image?.link))))
            }

            return DriverPostViewObj(
                model.id,
                PlaceViewObj(model.from.districtId,
                             model.from.regionId,
                             model.from.lat,
                             model.from.lon,
                             model.from.regionName,
                             model.from.districtName),
                PlaceViewObj(model.to.districtId,
                             model.to.regionId,
                             model.to.lat,
                             model.to.lon,
                             model.to.regionName,
                             model.to.districtName),
                model.price,
                model.departureDate,
                model.finishedDate,
                model.timeFirstPart,
                model.timeSecondPart,
                model.timeThirdPart,
                model.timeFourthPart,
                profile,
                model.carId,
                CarInPostViewObj(model.car!!.id,
                                 model.car.modelId,
                                 ImageViewObj(model.car.image!!.id,
                                              model.car.image!!.link),
                                 CarModelViewObj(model.car.carModel!!.id,
                                                 model.car.carModel!!.name),
                                 model.car.fuelType,
                                 model.car.colorId,
                                 model.car.carNumber,
                                 model.car.carYear,
                                 model.car.airConditioner),
                model.remark,
                model.seat,
                passengerList,
                model.postType
            )
        }
    }
}


@Parcelize
data class PassengerViewObj(var id: Long? = null,
                            var profile: ProfileViewObj? = null,
                            var submitDate: String? = null) : Parcelable

/**
 * Representation for a [CarInPost] fetched from the API
 */
@Parcelize
data class CarInPostViewObj(var id: Long? = null,
                            var modelId: Long? = null,
                            var image: ImageViewObj? = null,
                            var carModel: CarModelViewObj? = null,
                            var fuelType: EFuelType? = null,
                            var colorId: Long? = null,
                            var carNumber: String? = null,
                            var carYear: Int? = null,
                            var airConditioner: Boolean? = null) : Parcelable


@Parcelize
data class CarModelViewObj(val id: Long,
                           val name: String? = null) : Parcelable
