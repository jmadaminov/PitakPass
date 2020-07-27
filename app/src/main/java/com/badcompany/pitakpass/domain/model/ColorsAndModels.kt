package com.badcompany.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

data class ColorsAndModels (@SerializedName("colors") val colors: List<CarColor>,
                            @SerializedName("models") val models: List<CarModel>)