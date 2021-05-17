package com.novatec.epitak_passenger.domain.model

import com.google.gson.annotations.SerializedName

data class IdName(@SerializedName("id") var id: Long? = null,
                  @SerializedName("name") var name: String? = null)