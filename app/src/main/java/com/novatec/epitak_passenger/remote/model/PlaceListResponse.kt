package com.novatec.epitak_passenger.remote.model

import com.novatec.epitak_passenger.domain.model.Place

/**
 * Created by jahon on 12-Apr-20
 */
data class PlaceListResponse(val code: Int? = null,
                             val message: String? = null,
                             val data: List<Place>? = null)

