package com.novatec.pitakpass.domain.repository

import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.Place

interface PlaceRepository {

    suspend fun getPlacesAutocomplete( queryString:String): ResultWrapper<List<Place>>


}