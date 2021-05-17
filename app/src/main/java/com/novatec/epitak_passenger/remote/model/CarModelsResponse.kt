package com.novatec.epitak_passenger.remote.model

import com.novatec.epitak_passenger.domain.model.CarModel

/**
 * Created by jahon on 12-Apr-20
 */
data class CarModelsResponse(val code: Int? = null,
                             val message: String? = null,
                             val data: List<CarModel>? = null
)


