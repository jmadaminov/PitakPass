package com.novatec.epitak_passenger.domain.repository

import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.User
import com.novatec.epitak_passenger.domain.model.UserCredentials
import com.novatec.epitak_passenger.domain.model.AuthBody
import com.novatec.epitak_passenger.util.ResponseWrapper

interface UserRepository {

    suspend fun loginUser(phoneNum: String): ResponseWrapper<UserCredentials?>
    suspend fun registerUser(user: User): ResultWrapper<String>
    suspend fun smsConfirm(userCredentials: UserCredentials): ResultWrapper<AuthBody>
    suspend fun sendFeedback(feedback:String): ResponseWrapper<Any?>
    suspend fun updateUserInfo(name: String, surName: String, uploadedAvatarId: Long?): ResponseWrapper<Any?>
    suspend fun getActiveAppVersions(): ResponseWrapper<List<String>>

}