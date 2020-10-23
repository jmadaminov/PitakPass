package com.badcompany.pitakpass.data.source

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.repository.UserDataStore
import com.badcompany.pitakpass.data.repository.UserRemote
import com.badcompany.pitakpass.domain.model.AuthBody
import com.badcompany.pitakpass.domain.model.User
import com.badcompany.pitakpass.domain.model.UserCredentials
import javax.inject.Inject

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class UserRemoteDataStore @Inject constructor(private val userRemote: UserRemote) :
    UserDataStore {

//    override fun clearBufferoos(): Completable {
//        throw UnsupportedOperationException()
//    }
//
//    override fun saveBufferoos(bufferoos: List<BufferooEntity>): Completable {
//        throw UnsupportedOperationException()
//    }
//
//    /**
//     * Retrieve a list of [BufferooEntity] instances from the API
//     */
    override suspend fun userLogin(phoneNum: String): ResultWrapper<String> {
        return userRemote.loginUser(phoneNum)
    }

    override suspend fun userRegister(user: User): ResultWrapper<String>  {
        return userRemote.registerUser(user)
    }
    override suspend fun confirmSms(userCredentialsEntity: UserCredentials): ResultWrapper<AuthBody> {
        return userRemote.confirmUser(userCredentialsEntity)
    }

    override suspend fun sendFeedback(feedback: String)=userRemote.sendFeedback(feedback)

}