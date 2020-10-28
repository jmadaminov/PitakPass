package com.badcompany.pitakpass.data.source

import com.badcompany.pitakpass.data.repository.PlaceDataStore
import com.badcompany.pitakpass.data.repository.PlaceRemote
import com.badcompany.pitakpass.domain.model.Place
import com.badcompany.pitakpass.util.ResultWrapper
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class PlaceRemoteDataStore @Inject constructor(private val placeRemote: PlaceRemote) :
    PlaceDataStore {
    override suspend fun getPlacesAutocomplete(queryString: String): ResultWrapper<List<Place>> {
        return placeRemote.getPlacesAutocomplete(queryString)
    }


}