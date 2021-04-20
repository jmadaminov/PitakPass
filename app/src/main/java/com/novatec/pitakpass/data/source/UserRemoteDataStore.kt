package com.novatec.pitakpass.data.source

import com.novatec.pitakpass.data.repository.UserDataStore
import com.novatec.pitakpass.data.repository.UserRemote
import com.novatec.pitakpass.domain.model.User
import com.novatec.pitakpass.domain.model.UserCredentials
import com.novatec.pitakpass.util.ResponseWrapper
import javax.inject.Inject

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class UserRemoteDataStore @Inject constructor(private val userRemote: UserRemote) :
    UserDataStore {

    override suspend fun userLogin(phoneNum: String) = userRemote.loginUser(phoneNum)

    override suspend fun userRegister(user: User) = userRemote.registerUser(user)

    override suspend fun confirmSms(userCredentials: UserCredentials) =
        userRemote.confirmUser(userCredentials)

    override suspend fun sendFeedback(feedback: String) = userRemote.sendFeedback(feedback)
    override suspend fun updateUserInfo(name: String,
                                        surName: String,
                                        uploadedAvatarId: Long?) = userRemote.updateUserInfo(name, surName, uploadedAvatarId)

    override suspend fun getActiveAppVersions() = userRemote.getActiveAppVersions()

}