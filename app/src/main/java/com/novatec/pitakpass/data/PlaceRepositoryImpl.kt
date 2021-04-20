package com.novatec.pitakpass.data

import com.novatec.pitakpass.data.source.PlaceDataStoreFactory
import com.novatec.pitakpass.domain.model.Place
import com.novatec.pitakpass.domain.repository.PlaceRepository
import com.novatec.pitakpass.util.ErrorWrapper
import com.novatec.pitakpass.util.ResultWrapper
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class PlaceRepositoryImpl @Inject constructor(private val factory: PlaceDataStoreFactory) :
    PlaceRepository {


    override suspend fun getPlacesAutocomplete(
                                               queryString: String): ResultWrapper<List<Place>> {

        val response =
            factory.retrieveDataStore(false).getPlacesAutocomplete( queryString)
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(response.value)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }

    }


}