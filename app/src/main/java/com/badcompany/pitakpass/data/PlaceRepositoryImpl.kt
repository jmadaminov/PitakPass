package com.badcompany.pitakpass.data

import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.mapper.PlaceMapper
import com.badcompany.pitakpass.data.source.PlaceDataStoreFactory
import com.badcompany.pitakpass.domain.domainmodel.Place
import com.badcompany.pitakpass.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class PlaceRepositoryImpl @Inject constructor(private val factory: PlaceDataStoreFactory,
                                              private val placeMapper: PlaceMapper) :
    PlaceRepository {


    override suspend fun getPlacesAutocomplete(token: String, lang: String,
                                               queryString: String): ResultWrapper<List<Place>> {

        val response =
            factory.retrieveDataStore(false).getPlacesAutocomplete(token, lang, queryString)
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val places = arrayListOf<Place>()
                response.value.forEach {
                    places.add(placeMapper.mapFromEntity(it))
                }
                ResultWrapper.Success(places)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }

    }


}