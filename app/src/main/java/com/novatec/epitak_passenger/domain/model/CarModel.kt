package com.novatec.epitak_passenger.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarModel (@SerializedName("id") val id: Long,
                     @SerializedName("name") val name: String):
    Parcelable