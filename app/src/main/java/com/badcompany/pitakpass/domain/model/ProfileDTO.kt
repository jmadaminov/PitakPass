package com.badcompany.pitakpass.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileDTO(@SerializedName("phoneNum") val phoneNum: String,
                      @SerializedName("name") val name: String,
                      @SerializedName("surname") val surname: String,
                      @SerializedName("id") val id: String,
                      @SerializedName("image") val image: Image?=null):Parcelable
