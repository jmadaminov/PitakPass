package com.novatec.pitakpass.data.repository

import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.Place


interface PlaceDataStore {
    suspend fun getPlacesAutocomplete( queryString:String): ResultWrapper<List<Place>>

}