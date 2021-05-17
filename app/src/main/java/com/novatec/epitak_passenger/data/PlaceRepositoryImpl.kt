package com.novatec.epitak_passenger.data

import com.novatec.epitak_passenger.data.source.PlaceDataStoreFactory
import com.novatec.epitak_passenger.domain.model.Place
import com.novatec.epitak_passenger.domain.repository.PlaceRepository
import com.novatec.epitak_passenger.util.ErrorWrapper
import com.novatec.epitak_passenger.util.ResultWrapper
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