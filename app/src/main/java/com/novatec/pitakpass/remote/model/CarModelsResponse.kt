package com.novatec.pitakpass.remote.model

import com.novatec.pitakpass.domain.model.CarModel

/**
 * Created by jahon on 12-Apr-20
 */
data class CarModelsResponse(val code: Int? = null,
                             val message: String? = null,
                             val data: List<CarModel>? = null
)


