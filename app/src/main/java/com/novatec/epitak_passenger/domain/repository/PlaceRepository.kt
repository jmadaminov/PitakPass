package com.novatec.epitak_passenger.domain.repository

import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.Place

interface PlaceRepository {

    suspend fun getPlacesAutocomplete( queryString:String): ResultWrapper<List<Place>>


}