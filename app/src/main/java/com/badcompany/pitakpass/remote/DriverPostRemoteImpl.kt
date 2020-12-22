package com.badcompany.pitakpass.remote

import com.badcompany.pitakpass.data.repository.DriverPostRemote
import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.domain.model.Filter
import com.badcompany.pitakpass.domain.model.PassengerOffer
import com.badcompany.pitakpass.util.ResponseWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.getFormattedResponse
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class DriverPostRemoteImpl @Inject constructor(private val apiService: ApiService,
                                               private val authorizedApiService: AuthorizedApiService) :
    DriverPostRemote {

    override suspend fun filterDriverPost(filter: Filter) =
        getFormattedResponse { authorizedApiService.filterDriverPost(filter) }


    override suspend fun getPostById(id: Int): ResponseWrapper<DriverPost> =
        getFormattedResponse { authorizedApiService.getDriverPostById(id) }

    override suspend fun joinARide(myOffer: PassengerOffer) =
        getFormattedResponse { authorizedApiService.joinARide(myOffer) }

}