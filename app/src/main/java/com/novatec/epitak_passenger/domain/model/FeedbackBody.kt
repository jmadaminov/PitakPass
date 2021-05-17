package com.novatec.epitak_passenger.domain.model

import com.novatec.epitak_passenger.util.Constants
import com.google.gson.annotations.SerializedName

data class FeedbackBody(@SerializedName("content") var content: String? = null,
                        @SerializedName("type") var type: String = Constants.ROLE_PASSENGER)

