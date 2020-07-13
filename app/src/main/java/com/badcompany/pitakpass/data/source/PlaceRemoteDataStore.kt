package com.badcompany.pitakpass.data.source

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.model.PlaceEntity
import com.badcompany.pitakpass.data.repository.PlaceDataStore
import com.badcompany.pitakpass.data.repository.PlaceRemote
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class PlaceRemoteDataStore @Inject constructor(private val placeRemote: PlaceRemote) :
    PlaceDataStore {
    override suspend fun getPlacesAutocomplete(token: String,lang: String,
                                               queryString: String): ResultWrapper<List<PlaceEntity>> {

        return placeRemote.getPlacesAutocomplete(token, lang,queryString)
    }


}