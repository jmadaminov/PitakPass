package com.badcompany.pitakpass.data.repository

import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.model.AuthEntity
import com.badcompany.pitakpass.data.model.UserCredentialsEntity
import com.badcompany.pitakpass.data.model.UserEntity
import com.badcompany.pitakpass.domain.domainmodel.AuthBody


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface UserRemote {

    /**
     * Retrieve a list of Bufferoos, from the cache
     */
   suspend fun loginUser(phoneNum: String): ResultWrapper<String>

    suspend  fun registerUser(user: UserEntity): ResultWrapper<String>
    suspend  fun confirmUser(user: UserCredentialsEntity): ResultWrapper<AuthEntity>

}