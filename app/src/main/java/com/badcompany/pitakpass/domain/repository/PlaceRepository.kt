package com.badcompany.pitakpass.domain.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.model.Place

interface PlaceRepository {

    suspend fun getPlacesAutocomplete( queryString:String): ResultWrapper<List<Place>>


}