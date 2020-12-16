package com.badcompany.pitakpass.data.repository

import com.badcompany.pitakpass.domain.model.AuthBody
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.model.User
import com.badcompany.pitakpass.domain.model.UserCredentials
import com.badcompany.pitakpass.util.ResponseWrapper


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

}