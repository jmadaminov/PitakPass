package com.badcompany.pitakpass.data.repository

import com.badcompany.pitakpass.domain.model.AuthBody
import com.badcompany.pitakpass.domain.model.User
import com.badcompany.pitakpass.domain.model.UserCredentials
import com.badcompany.pitakpass.util.ResponseWrapper
import com.badcompany.pitakpass.util.ResultWrapper


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface UserRemote {

    /**
     * Retrieve a list of Bufferoos, from the cache
     */
    suspend fun loginUser(phoneNum: String): ResponseWrapper<UserCredentials?>

    suspend fun registerUser(user: User): ResultWrapper<String>
    suspend fun confirmUser(user: UserCredentials): ResultWrapper<AuthBody>
    suspend fun sendFeedback(feedback:String): ResponseWrapper<Any?>
    suspend fun updateUserInfo(name: String, surName: String, uploadedAvatarId: Long?): ResponseWrapper<Any?>
    suspend fun getActiveAppVersions(): ResponseWrapper<List<String>>
}