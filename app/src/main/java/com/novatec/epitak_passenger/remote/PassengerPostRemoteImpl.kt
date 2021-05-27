package com.novatec.epitak_passenger.remote

import com.novatec.epitak_passenger.core.enums.EPostType
import com.novatec.epitak_passenger.data.repository.PassengerPostRemote
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.util.*
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PassengerPostRemoteImpl @Inject constructor(private val authApiService: AuthApiService) :
    PassengerPostRemote {

    override suspend fun createPost(post: PassengerPost): ResultWrapper<PassengerPost?> {
        val response = if (post.id != null) getFormattedResponseNullable {
            authApiService.editPost(post.id, post)
        } else getFormattedResponse {
            if (post.postType == EPostType.PARCEL_SM) authApiService.createParcelPost(post)
            else authApiService.createPost(post)
        }

        return when (response) {
            is ResponseError -> {
                ErrorWrapper.ResponseError(message = response.message, code = response.code)
            }
            is ResponseSuccess -> {
                ResultWrapper.Success(response.value)
            }
        }

    }

    override suspend fun deletePost(identifier: String): ResultWrapper<Unit> {
        return try {
            val response = authApiService.deletePost(identifier)
            if (response.code == 1) ResultWrapper.Success(Unit)
            else ErrorWrapper.ResponseError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun finishPost(id: String): ResultWrapper<Unit> {
        return try {
            val response = authApiService.finishPost(id)
            if (response.code == 1) ResultWrapper.Success(Unit)
            else ErrorWrapper.ResponseError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getActivePosts(): ResultWrapper<List<PassengerPost>> {
        return try {
            val response = authApiService.getActivePosts()
            if (response.code == 1) ResultWrapper.Success(response.data!!)
            else ErrorWrapper.ResponseError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getHistoryPosts(page: Int): ResultWrapper<List<PassengerPost>> {

        return try {
            val response = authApiService.getHistoryPosts(page)
            if (response.code == 1) {
                val posts = arrayListOf<PassengerPost>()
                response.data?.data?.forEach { posts.add(it) }
                ResultWrapper.Success(posts)
            } else ErrorWrapper.ResponseError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getPassengerPostById(id: Long) =
        getFormattedResponse { authApiService.getPassengerPostById(id) }

    override suspend fun acceptOffer(id: Long) =
        getFormattedResponseNullable { authApiService.acceptOffer(id) }

    override suspend fun rejectOffer(id: Long) =
        getFormattedResponseNullable { authApiService.rejectOffer(id) }

    override suspend fun cancelMyOffer(id: Long) =
        getFormattedResponseNullable { authApiService.cancelMyOffer(id) }


}