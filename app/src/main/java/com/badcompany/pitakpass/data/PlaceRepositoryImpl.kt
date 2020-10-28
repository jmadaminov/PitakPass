package com.badcompany.pitakpass.data

import com.badcompany.pitakpass.data.source.PlaceDataStoreFactory
import com.badcompany.pitakpass.domain.model.Place
import com.badcompany.pitakpass.domain.repository.PlaceRepository
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
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