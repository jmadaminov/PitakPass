package com.novatec.pitakpass.data.source

import com.novatec.pitakpass.data.repository.PlaceDataStore
import com.novatec.pitakpass.data.repository.PlaceRemote
import com.novatec.pitakpass.domain.model.Place
import com.novatec.pitakpass.util.ResultWrapper
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