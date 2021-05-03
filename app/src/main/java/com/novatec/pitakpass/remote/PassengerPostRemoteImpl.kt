package com.novatec.pitakpass.remote

import com.novatec.pitakpass.data.repository.PassengerPostRemote
import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.util.*
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
        post: PassengerPost): ResultWrapper<PassengerPost> {
        val response =

        if (post.id != null) getFormattedResponseNullable { authorizedApiService.editPost(post.id, post) }
        else getFormattedResponse { authorizedApiService.createPost(post) }

        (return when (response) {
            is ResponseError -> {
                ErrorWrapper.ResponseError(message = response.message, code = response.code)
            }
            is ResponseSuccess -> {
                ResultWrapper.Success(response.value)
            }
        }).exhaustive

    }

    override suspend fun deletePassengerPost(
        identifier: String): ResultWrapper<Unit> {
        return try {
            val response = authorizedApiService.deletePost(identifier)
            if (response.code == 1) {
                ResultWrapper.Success(Unit)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun finishPassengerPost(
        identifier: String): ResultWrapper<Unit> {
        return try {
            val response = authorizedApiService.finishPost(identifier)
            if (response.code == 1) {
                ResultWrapper.Success(Unit)
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

    override suspend fun getHistoryPassengerPosts(page: Int): ResultWrapper<List<PassengerPost>> {

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

    override suspend fun getPassengerPostById(id: Long) =
        getFormattedResponse { authorizedApiService.getPassengerPostById(id) }

    override suspend fun acceptOffer(id: Long) =
        getFormattedResponseNullable { authorizedApiService.acceptOffer(id) }

    override suspend fun rejectOffer(id: Long) =
        getFormattedResponseNullable { authorizedApiService.rejectOffer(id) }

    override suspend fun cancelMyOffer(id: Long) =
        getFormattedResponseNullable { authorizedApiService.cancelMyOffer(id) }


}