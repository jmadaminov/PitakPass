package com.badcompany.pitakpass.domain.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.PassengerPost
import com.badcompany.pitakpass.domain.domainmodel.Place

interface PlaceRepository {

    suspend fun getPlacesAutocomplete(token: String,lang: String, queryString:String): ResultWrapper<List<Place>>


}