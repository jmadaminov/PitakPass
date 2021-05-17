package com.novatec.epitak_passenger.remote.model

import com.novatec.epitak_passenger.domain.model.AuthBody
import com.google.gson.annotations.SerializedName

/**
 * Created by jahon on 12-Apr-20
 */
data class AuthSuccessResponse(val code: Int? = null,
                               val message: String? = null,
                               val data: AuthBody? = null)
