package com.novatec.epitak_passenger.data

import com.novatec.epitak_passenger.data.source.UserDataStoreFactory
import com.novatec.epitak_passenger.domain.model.User
import com.novatec.epitak_passenger.domain.model.UserCredentials
import com.novatec.epitak_passenger.domain.repository.UserRepository
import com.novatec.epitak_passenger.util.ResponseWrapper
import javax.inject.Inject

/**
 * Provides an implementation of the [UserRepository] interface for communicating to and from
 * data sources
 */
class UserRepositoryImpl @Inject constructor(private val factory: UserDataStoreFactory) :
    UserRepository {

    override suspend fun loginUser(phoneNum: String) =
        factory.retrieveDataStore(false).userLogin(phoneNum)

    override suspend fun registerUser(user: User) =
        factory.retrieveDataStore(false).userRegister(user)

    override suspend fun smsConfirm(userCredentials: UserCredentials) =
        factory.retrieveDataStore(false).confirmSms(userCredentials)

    override suspend fun sendFeedback(feedback: String) = factory.retrieveDataStore(false)
        .sendFeedback(feedback)

    override suspend fun updateUserInfo(name: String,
                                        surName: String,
                                        uploadedAvatarId: Long?) = factory.retrieveDataStore(false)
        .updateUserInfo(name, surName, uploadedAvatarId)

    override suspend fun getActiveAppVersions() = factory.retrieveDataStore(false)
        .getActiveAppVersions()
}