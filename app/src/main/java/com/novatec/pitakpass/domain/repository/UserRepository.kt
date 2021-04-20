package com.novatec.pitakpass.domain.repository

import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.User
import com.novatec.pitakpass.domain.model.UserCredentials
import com.novatec.pitakpass.domain.model.AuthBody
import com.novatec.pitakpass.util.ResponseWrapper

interface UserRepository {

    suspend fun loginUser(phoneNum: String): ResponseWrapper<UserCredentials?>
    suspend fun registerUser(user: User): ResultWrapper<String>
    suspend fun smsConfirm(userCredentials: UserCredentials): ResultWrapper<AuthBody>
    suspend fun sendFeedback(feedback:String): ResponseWrapper<Any?>
    suspend fun updateUserInfo(name: String, surName: String, uploadedAvatarId: Long?): ResponseWrapper<Any?>
    suspend fun getActiveAppVersions(): ResponseWrapper<List<String>>

}