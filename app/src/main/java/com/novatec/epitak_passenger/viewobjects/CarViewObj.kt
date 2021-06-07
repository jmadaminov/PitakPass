package com.novatec.epitak_passenger.viewobjects

import android.os.Parcelable
import com.novatec.epitak_passenger.domain.model.PhotoBody
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarViewObj(
    var id: Long? = null,
    var carModel: IdNameViewObj? = null,
    var image: ImageViewObj = ImageViewObj(),
    var fuelType: String? = null,
    var carColor: CarColorViewObj? = null,
    var carNumber: String? = null,
    var carYear: Int? = null,
    var airConditioner: Boolean? = null,
    var def: Boolean? = null,
    var imageList: List<ImageViewObj> = arrayListOf()
) : Parcelable

@Parcelize
data class IdNameViewObj(
    var id: Long? = null,
    var name: String? = null
) : Parcelable

@Parcelize
data class ImageViewObj(
    var id: Long? = null,
    var link: String? = null
) : Parcelable {
    fun toImageBody(): PhotoBody {
        return PhotoBody(id = id, link = link)
    }
}

@Parcelize
data class CarColorViewObj(
    val id: Long? = null,
    val hex: String? = null,
    val nameEn: String? = null,
    val nameUz: String? = null,
    val nameRu: String? = null
) : Parcelable