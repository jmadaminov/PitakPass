package com.badcompany.pitakpass.data

import com.badcompany.pitakpass.data.source.DriverPostDataStoreFactory
import com.badcompany.pitakpass.domain.model.Filter
import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.domain.model.PassengerOffer
import com.badcompany.pitakpass.domain.repository.DriverPostRepository
import com.badcompany.pitakpass.domain.repository.PlaceRepository
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResponseWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class DriverPostRepositoryImpl @Inject constructor(private val factoryDriver: DriverPostDataStoreFactory) :
    DriverPostRepository {
//    override suspend fun filterDriverPost(
//
//                                             filter: Filter): ResultWrapper<List<DriverPost>> {
//
//        val response = factoryDriver.retrieveDataStore(false)
//            .filterDriverPost( filter)
//
//        return when (response) {
//            is ErrorWrapper.ResponseError -> response
//            is ErrorWrapper.SystemError -> response
//            is ResultWrapper.Success -> {
//                ResultWrapper.Success(response.value)
//            }
//            ResultWrapper.InProgress -> ResultWrapper.InProgress
//        }
//    }

    override suspend fun getDriverPostById(id: Int) = factoryDriver.retrieveDataStore(false)
    .getPostById(id)

    override suspend fun joinARide(myOffer: PassengerOffer) = factoryDriver.retrieveDataStore(false)
        .joinARide(myOffer)


}

