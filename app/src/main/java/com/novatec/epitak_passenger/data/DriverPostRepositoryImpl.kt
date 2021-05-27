package com.novatec.epitak_passenger.data

import com.novatec.epitak_passenger.data.source.DriverPostDataStoreFactory
import com.novatec.epitak_passenger.domain.model.Offer
import com.novatec.epitak_passenger.domain.repository.DriverPostRepository
import com.novatec.epitak_passenger.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class DriverPostRepositoryImpl @Inject constructor(private val factoryDriver: DriverPostDataStoreFactory) :
    DriverPostRepository {

    override suspend fun getDriverPostById(id: Long) = factoryDriver.retrieveDataStore(false)
        .getPostById(id)

    override suspend fun sendOffer(myOffer: Offer) = factoryDriver.retrieveDataStore(false)
        .sendOffer(myOffer)

    override suspend fun getMyRatingForDriver(id: Long) = factoryDriver.retrieveDataStore(false)
        .getMyRatingForDriver(id)

    override suspend fun editMyRatingForDriver(ratingId: Long, id: Long, rating: Float) =
        factoryDriver.retrieveDataStore(false)
            .editMyRatingForDriver(ratingId, id, rating)

    override suspend fun postMyRatingForDriver(id: Long, rating: Float) =
        factoryDriver.retrieveDataStore(false)
            .postMyRatingForDriver(id, rating)

}

