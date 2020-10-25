package com.badcompany.pitakpass.remote

import com.badcompany.pitakpass.data.repository.DriverPostRemote
import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.domain.model.Filter
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResponseWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.getFormattedResponse
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class DriverPostRemoteImpl @Inject constructor(private val apiService: ApiService) :
    DriverPostRemote {

    override suspend fun filterDriverPost(token: String,
                                          lang: String,
                                          filter: Filter): ResultWrapper<List<DriverPost>> {
        return try {
            val response =
                apiService.filterDriverPost(token, lang, filter)
            if (response.code == 1) {
                ResultWrapper.Success(response.data!!.data!!)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getPostById(id: Int): ResponseWrapper<DriverPost> {
        return getFormattedResponse { apiService.getDriverPostById(id) }
    }


}