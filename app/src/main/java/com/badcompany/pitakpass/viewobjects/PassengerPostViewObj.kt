package com.badcompany.pitakpass.viewobjects

import android.os.Parcelable
import com.badcompany.pitakpass.ui.EPostType
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
                                val carId: Long? = null,
                                val car: CarViewObj? = null,
                                val remark: String?=null,
                                val seat: Int,
                                val postType: EPostType = EPostType.PASSENGER_SM) : Parcelable

@Parcelize
data class PlaceViewObj(val districtId: Int? = null,
                        val regionId: Int? = null,
                        val lat: Double? = null,
                        val lon: Double? = null,
                        val regionName: String? = null,
                        val name: String? = null) : Parcelable