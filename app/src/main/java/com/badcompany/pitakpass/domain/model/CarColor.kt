package com.badcompany.pitakpass.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize

data class CarColor(@SerializedName("id") val id: Long,
                    @SerializedName("hex") val hex: String,
                    @SerializedName("name") val name: String? = null): Parcelable