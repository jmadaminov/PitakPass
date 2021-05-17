package com.novatec.epitak_passenger.viewobjects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileViewObj(val phoneNum: String?=null,
                          val name: String?=null,
                          val surname: String?=null,
                          val id: String?=null,
                          val image: ImageViewObj? = null) : Parcelable