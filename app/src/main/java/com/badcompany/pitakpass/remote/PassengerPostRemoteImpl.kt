package com.badcompany.pitakpass.remote

import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.model.FilterEntity
import com.badcompany.pitakpass.data.model.PassengerPostEntity
import com.badcompany.pitakpass.data.repository.PassengerPostRemote
import com.badcompany.pitakpass.remote.mapper.FilterMapper
import com.badcompany.pitakpass.remote.mapper.PassengerPostMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PassengerPostRemoteImpl @Inject constructor(private val apiService: ApiService,
                                                  private val postMapper: PassengerPostMapper,
                                                  private val filterMapper: FilterMapper) :
    PassengerPostRemote {

    override suspend fun filterPassengerPost(token: String,
                                             lang: String,
                                             filter: FilterEntity): ResultWrapper<List<PassengerPostEntity>> {
        return try {
            val response =
                apiService.filterPassengerPost(token, lang, filterMapper.mapFromEntity(filter))
            if (response.code == 1) {
                val posts = arrayListOf<PassengerPostEntity>()
                response.data?.data?.forEach { posts.add(postMapper.mapToEntity(it)) }
                ResultWrapper.Success(posts)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }


}