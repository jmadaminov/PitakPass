package com.badcompany.pitakpass.domain.model

import com.badcompany.pitakpass.util.Constants
import com.google.gson.annotations.SerializedName

data class FeedbackBody(@SerializedName("content") var content: String? = null,
                        @SerializedName("type") var type: String = Constants.ROLE_PASSENGER)

