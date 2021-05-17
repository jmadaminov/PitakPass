package com.novatec.epitak_passenger.data.repository

import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.Place


interface PlaceDataStore {
    suspend fun getPlacesAutocomplete( queryString:String): ResultWrapper<List<Place>>

}