package com.novatec.epitak_passenger.data.source

import com.novatec.epitak_passenger.data.repository.UserDataStore
import com.novatec.epitak_passenger.data.repository.UserRemote
import com.novatec.epitak_passenger.domain.model.User
import com.novatec.epitak_passenger.domain.model.UserCredentials
import com.novatec.epitak_passenger.util.ResponseWrapper
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