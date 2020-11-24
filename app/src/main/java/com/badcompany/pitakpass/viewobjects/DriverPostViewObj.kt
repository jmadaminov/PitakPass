package com.badcompany.pitakpass.viewobjects

import android.os.Parcelable
import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.ui.EPostType
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
                             val carId: Long? = null,
                             val car: CarInPostViewObj? = null,
                             val remark: String,
                             val seat: Int,
                             val postType: EPostType) : Parcelable {

    companion object {
        fun mapFromDriverPostModel(model: DriverPost): DriverPostViewObj {
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
                model.postType
            )
        }
    }
}


/**
 * Representation for a [CarInPost] fetched from the API
 */
@Parcelize
data class CarInPostViewObj(var id: Long? = null,
                            var modelId: Long? = null,
                            var image: ImageViewObj? = null,
                            var carModel: CarModelViewObj? = null,
                            var fuelType: String? = null,
                            var colorId: Long? = null,
                            var carNumber: String? = null,
                            var carYear: Int? = null,
                            var airConditioner: Boolean? = null) : Parcelable


@Parcelize
data class CarModelViewObj(val id: Long,
                           val name: String) : Parcelable
