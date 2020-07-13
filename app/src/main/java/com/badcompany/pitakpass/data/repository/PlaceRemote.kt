package com.badcompany.pitakpass.data.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.model.PlaceEntity

interface PlaceRemote {

   suspend fun getPlacesAutocomplete(token: String,lang: String, queryString:String): ResultWrapper<List<PlaceEntity>>

}