package com.novatec.epitak_passenger.remote

import com.novatec.epitak_passenger.data.repository.DriverPostRemote
import com.novatec.epitak_passenger.domain.model.DriverPost
import com.novatec.epitak_passenger.domain.model.Filter
import com.novatec.epitak_passenger.domain.model.PassengerOffer
import com.novatec.epitak_passenger.remote.model.ObjRating
import com.novatec.epitak_passenger.util.ResponseWrapper
import com.novatec.epitak_passenger.util.getFormattedResponse
import com.novatec.epitak_passenger.util.getFormattedResponseNullable
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class DriverPostRemoteImpl @Inject constructor(private val apiService: ApiService,
                                               private val authApiService: AuthApiService) :
    DriverPostRemote {

    override suspend fun filterDriverPost(filter: Filter) =
        getFormattedResponse { authApiService.filterDriverPost(filter) }


    override suspend fun getPostById(id: Long): ResponseWrapper<DriverPost> =
        getFormattedResponse { authApiService.getDriverPostById(id) }

    override suspend fun joinARide(myOffer: PassengerOffer) =
        getFormattedResponse { authApiService.joinARide(myOffer) }

    override suspend fun getMyRatingForDriver(id: Long) =
        getFormattedResponseNullable { authApiService.getMyRatingForDriver(id) }

    override suspend fun editMyRatingForDriver(ratingId: Long, id: Long, rating: Float) =
        getFormattedResponseNullable {
            authApiService.editMyRatingForDriver(ratingId,
                                                       ObjRating(driverId = id,
                                                                 id = ratingId,
                                                                 rating = rating))
        }

    override suspend fun postMyRatingForDriver(id: Long, rating: Float) =
        getFormattedResponse {
            authApiService.postMyRatingForDriver(ObjRating(driverId = id, rating = rating))
        }

}