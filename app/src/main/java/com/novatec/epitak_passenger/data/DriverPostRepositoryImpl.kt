package com.novatec.epitak_passenger.data

import com.novatec.epitak_passenger.data.source.DriverPostDataStoreFactory
import com.novatec.epitak_passenger.domain.model.PassengerOffer
import com.novatec.epitak_passenger.domain.repository.DriverPostRepository
import com.novatec.epitak_passenger.domain.repository.PlaceRepository
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

    override suspend fun getDriverPostById(id: Long) = factoryDriver.retrieveDataStore(false)
        .getPostById(id)

    override suspend fun joinARide(myOffer: PassengerOffer) = factoryDriver.retrieveDataStore(false)
        .joinARide(myOffer)

    override suspend fun getMyRatingForDriver(id: Long) = factoryDriver.retrieveDataStore(false)
        .getMyRatingForDriver(id)

    override suspend fun editMyRatingForDriver(ratingId:Long,id: Long, rating: Float) =
        factoryDriver.retrieveDataStore(false)
            .editMyRatingForDriver(ratingId,id, rating)

    override suspend fun postMyRatingForDriver(id: Long, rating: Float) =
        factoryDriver.retrieveDataStore(false)
            .postMyRatingForDriver(id, rating)


}

