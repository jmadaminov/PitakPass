package com.badcompany.pitakpass.remote

import com.badcompany.pitakpass.data.repository.PassengerPostRemote
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PassengerPostRemoteImpl @Inject constructor(private val apiService: ApiService,
                                                  private val authorizedApiService: AuthorizedApiService) :
    PassengerPostRemote {

    override suspend fun createPassengerPost(
                                             post: PassengerPost): ResultWrapper<String> {

        return try {
            val response = authorizedApiService.createPost(post)
            if (response.code == 1) {
                ResultWrapper.Success("SUCCESS")
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun deletePassengerPost(
                                             identifier: String): ResultWrapper<String> {
        return try {
            val response = authorizedApiService.deletePost(identifier)
            if (response.code == 1) {
                ResultWrapper.Success("SUCCESS")
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun finishPassengerPost(
                                             identifier: String): ResultWrapper<String> {
        return try {
            val response = authorizedApiService.finishPost(identifier)
            if (response.code == 1) {
                ResultWrapper.Success("SUCCESS")
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getActivePassengerPosts(
                                                 ): ResultWrapper<List<PassengerPost>> {

        return try {
            val response = authorizedApiService.getActivePosts()
            if (response.code == 1) {
                ResultWrapper.Success(response.data!!)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getHistoryPassengerPosts(                                                  page: Int): ResultWrapper<List<PassengerPost>> {

        return try {
            val response = authorizedApiService.getHistoryPosts(page)
            if (response.code == 1) {
                val posts = arrayListOf<PassengerPost>()
                response.data?.data?.forEach { posts.add(it) }
                ResultWrapper.Success(posts)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }


}