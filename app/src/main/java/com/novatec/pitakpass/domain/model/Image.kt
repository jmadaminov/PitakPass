package com.novatec.pitakpass.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(@SerializedName("link") var link: String? = null,
                 @SerializedName("id") var id: Long? = null):Parcelable