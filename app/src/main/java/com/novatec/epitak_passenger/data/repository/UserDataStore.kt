package com.novatec.epitak_passenger.data.repository

import com.novatec.epitak_passenger.domain.model.AuthBody
import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.User
import com.novatec.epitak_passenger.domain.model.UserCredentials
import com.novatec.epitak_passenger.util.ResponseWrapper


/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface UserDataStore {

    suspend fun userLogin(phoneNum: String): ResponseWrapper<UserCredentials?>
    suspend fun userRegister(user: User): ResultWrapper<String>
    suspend fun confirmSms(userCredentials: UserCredentials): ResultWrapper<AuthBody>
    suspend fun sendFeedback(feedback:String): ResponseWrapper<Any?>
    suspend fun updateUserInfo(name: String, surName: String, uploadedAvatarId: Long?): ResponseWrapper<Any?>
    suspend fun getActiveAppVersions(): ResponseWrapper<List<String>>
}