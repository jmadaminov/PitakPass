package com.novatec.epitak_passenger.data.source

import com.novatec.epitak_passenger.data.repository.PlaceDataStore
import com.novatec.epitak_passenger.data.repository.PlaceRemote
import com.novatec.epitak_passenger.domain.model.Place
import com.novatec.epitak_passenger.util.ResultWrapper
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