package com.novatec.epitak_passenger.remote.model


import com.google.gson.annotations.SerializedName

data class IdVersionDTO(@SerializedName("id") val id: Int,
                        @SerializedName("version") val version: String)
